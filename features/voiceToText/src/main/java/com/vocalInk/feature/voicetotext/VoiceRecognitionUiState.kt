package com.vocalInk.feature.voicetotext

data class VoiceRecognitionUiState(
    val recognizedText: String? = "",
    val listeningState: RecognitionState = RecognitionState.IDLE,
    val errorText: String? = null,
    val timer: String? = null
)
