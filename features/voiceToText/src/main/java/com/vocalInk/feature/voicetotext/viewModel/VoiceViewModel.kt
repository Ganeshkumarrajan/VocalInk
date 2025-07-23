package com.vocalInk.feature.voicetotext.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.voice.GetRemainingTimeUseCase
import com.example.domain.voice.usecase.SaveVoiceTextUseCase
import com.moshi.voice.VoiceToTextInterface
import com.vocalInk.feature.voicetotext.model.RecognitionState
import com.vocalInk.feature.voicetotext.model.VoiceRecognitionUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * ViewModel for managing voice-to-text recognition and timer logic.
 *
 * Responsibilities:
 * - Starts and stops voice recognition via [VoiceToTextInterface]
 * - Emits UI state updates through [uiState]
 * - Starts a countdown timer using [getRemainingTimeUseCase]
 * - Emits a close event through [closeOnSave] once text is saved
 */
@HiltViewModel
class VoiceViewModel @Inject constructor(
    private val voiceToTextManager: VoiceToTextInterface,
    private val getRemainingTimeUseCase: GetRemainingTimeUseCase,
    private val saveVoiceTextUseCase: SaveVoiceTextUseCase
) : ViewModel() {

    companion object {
        private const val DEFAULT_TIMER_SECONDS = 7
    }

    private var timerJob: Job? = null

    private val _uiState = MutableStateFlow(VoiceRecognitionUiState())
    val uiState: StateFlow<VoiceRecognitionUiState> = _uiState.asStateFlow()

    private val _closeOnSave = MutableSharedFlow<Boolean>()
    val closeOnSave: SharedFlow<Boolean> = _closeOnSave.asSharedFlow()

    /**
     * Saves the recognized voice text and emits a close signal to the screen.
     */
    fun onSave(voiceText: String) {
        viewModelScope.launch {
            saveVoiceTextUseCase(voiceText)
            _closeOnSave.emit(true)
        }
    }

    /**
     * Starts the voice recognition process and begins the timer countdown.
     */
    fun startListening() {
        _uiState.update { it.copy(listeningState = RecognitionState.LISTENING) }

        voiceToTextManager.startListening(
            onResult = { result ->
                _uiState.update {
                    it.copy(
                        recognizedText = result,
                        errorText = null,
                        listeningState = RecognitionState.FINISHED
                    )
                }
            },
            onError = { error ->
                updateError(error)
            },
            onEnd = {
                stopListening()
            }
        )

        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        timerJob = viewModelScope.launch {
            getRemainingTimeUseCase(DEFAULT_TIMER_SECONDS).collect { result ->
                result.onSuccess { seconds ->
                    updateTimerText(seconds)
                    if (seconds == 0) stopListening()
                }.onFailure { error ->
                    updateError(error.localizedMessage ?: "An unexpected error occurred.")
                }
            }
        }
    }

    private fun updateTimerText(seconds: Int) {
        val minutes = seconds / 60
        val secs = seconds % 60
        _uiState.update {
            it.copy(timer = "$minutes:${secs.toString().padStart(2, '0')}")
        }
    }

    /**
     * Stops the recognition and transitions to FINISHED unless already in ERROR state.
     */
    fun stopListening() {
        _uiState.update {
            if (it.listeningState != RecognitionState.ERROR) {
                it.copy(
                    errorText = null,
                    listeningState = RecognitionState.FINISHED
                )
            } else it
        }

        reset()
    }

    private fun updateError(message: String) {
        _uiState.update {
            it.copy(
                errorText = message,
                listeningState = RecognitionState.ERROR
            )
        }
    }

    private fun reset() {
        timerJob?.cancel()
    }

    /**
     * Cleans up resources and cancels timer when ViewModel is destroyed.
     */
    override fun onCleared() {
        super.onCleared()
        reset()
        voiceToTextManager.destroy()
        timerJob = null
    }
}
