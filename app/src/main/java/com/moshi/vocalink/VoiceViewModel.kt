package com.moshi.vocalink

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class VoiceViewModel @Inject constructor(private val voiceToTextManager: VoiceToTextManager) :
    ViewModel() {

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
    }

    fun stopListening() {
        _uiState.update {
            it.copy(listeningState = RecognitionState.STOPPED)
        }
    }

    private fun reset() {
        _uiState.value = VoiceRecognitionUiState()
    }

}


enum class RecognitionState {
    IDLE,
    LISTENING,
    STOPPED,
    ERROR,
}

data class VoiceRecognitionUiState(
    val recognizedText: String? = "",
    val listeningState: RecognitionState = RecognitionState.IDLE,
    val errorText: String? = null
)