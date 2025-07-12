package com.vocalInk.feature.voicetotext

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moshi.voice.VoiceToTextManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.Duration
import javax.inject.Inject

@HiltViewModel
@RequiresApi(Build.VERSION_CODES.O)
class VoiceViewModel @Inject constructor(
    private val voiceToTextManager: VoiceToTextManager
) : ViewModel() {

    companion object {
        private const val DEFAULT_TIMER_SECONDS = 15
        private val INITIAL_DURATION = Duration.ofSeconds(DEFAULT_TIMER_SECONDS.toLong())
    }

    private var job: Job? = null
    private var timeRemaining = INITIAL_DURATION

    private val _uiState = MutableStateFlow(VoiceRecognitionUiState())
    val uiState: StateFlow<VoiceRecognitionUiState> = _uiState.asStateFlow()

    fun startListening() {
        timeRemaining = INITIAL_DURATION
        _uiState.update { it.copy(listeningState = RecognitionState.LISTENING) }

        voiceToTextManager.startListening(
            onResult = { result ->
                _uiState.update { it.copy(recognizedText = result) }
            },
            onError = { error ->
                _uiState.update {
                    it.copy(
                        errorText = error,
                        listeningState = RecognitionState.ERROR
                    )
                }
            },
            onEd = {
                stopListening()
            }
        )

        startTimer()
    }

    fun stopListening() {
        _uiState.update { it.copy(listeningState = RecognitionState.FINISHED) }
        reset()
    }

    private fun reset() {
        job?.cancel()
        job = null
        timeRemaining = INITIAL_DURATION
    }

    private fun startTimer() {
        job?.cancel()
        job = viewModelScope.launch {
            while (!timeRemaining.isNegative) {
                updateTimerText()
                delay(1000)
                timeRemaining = timeRemaining.minusSeconds(1)
            }
            stopListening()
        }
    }

    private fun updateTimerText() {
        val minutes = timeRemaining.toMinutes()
        val seconds = timeRemaining.seconds % 60
        _uiState.update {
            it.copy(timer = "$minutes:${seconds.toString().padStart(2, '0')}")
        }
    }

    override fun onCleared() {
        super.onCleared()
        reset()
        voiceToTextManager.destroy()
    }
}

