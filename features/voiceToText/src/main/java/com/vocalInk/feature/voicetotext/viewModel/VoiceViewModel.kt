package com.vocalInk.feature.voicetotext.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.voice.usecase.SaveVoiceTextUseCase
import com.example.domain.timer.GetRemainingTimeUseCase
import com.moshi.voice.VoiceToTextInterface
import com.moshi.voice.VoiceToTextManager
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

    fun onSave(voiceText: String) {
        viewModelScope.launch {
            saveVoiceTextUseCase.invoke(voiceText)
            _closeOnSave.emit(true)
        }
    }

    fun startListening() {
        _uiState.update { it.copy(listeningState = RecognitionState.LISTENING) }

        voiceToTextManager.startListening(
            onResult = { result ->
                _uiState.update {
                    it.copy(
                        recognizedText = result,
                        errorText = null, // clear error on success
                        listeningState = RecognitionState.FINISHED // optional if you want a separate state
                    )
                }
            },
            onError = { error ->
                updateError(error)
            },
            onEd = {
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
                    updateError(error.localizedMessage ?: "Unknown error")
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

    fun stopListening() {
        _uiState.update {
            if (it.listeningState != RecognitionState.ERROR) {
                it.copy(
                    recognizedText = null,
                    errorText = null,
                    listeningState = RecognitionState.FINISHED)
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

    override fun onCleared() {
        super.onCleared()
        reset()
        voiceToTextManager.destroy()
    }
}