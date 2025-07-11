package com.vocalInk.feature.voicetotext

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
import javax.inject.Inject

@HiltViewModel
class VoiceViewModel @Inject constructor(private val voiceToTextManager: VoiceToTextManager) :
    ViewModel() {
    companion object {
        private const val DEFAULT_TIMER_SECONDS = 30
    }

    private var job: Job? = null
    private var seconds = DEFAULT_TIMER_SECONDS

    private val _uiState = MutableStateFlow(
        VoiceRecognitionUiState()
    )

    val uiState: StateFlow<VoiceRecognitionUiState> =
        _uiState.asStateFlow()

    fun startListening() {
        _uiState.update {
            it.copy(listeningState = RecognitionState.LISTENING)
        }

        voiceToTextManager.startListening(
            onResult = { result ->
                _uiState.update { it.copy(recognizedText = result) }
            },
            onError = { error ->

                reset()
                _uiState.update {
                    it.copy(
                        errorText = error,
                        listeningState = RecognitionState.ERROR
                    )
                }
            },
            onEd = {
                reset()
            }
        )

        startTimer()
    }

    fun stopListening() {
        _uiState.update {
            it.copy(listeningState = RecognitionState.STOPPED)
        }
        job?.cancel()
    }

    private fun reset() {
        job?.cancel()
        job = null
        _uiState.value = VoiceRecognitionUiState()
        seconds = DEFAULT_TIMER_SECONDS
    }


    private fun startTimer() {
        job?.cancel()
        job = viewModelScope.launch {
            while (seconds > 0) {
                delay(1000)
                seconds--
                val mins = seconds / 60
                val secs = seconds % 60
                _uiState.update {
                    it.copy(timer = "$mins:${secs.toString().padStart(2, '0')}")
                }
            }
            stopListening()
        }
    }

}
