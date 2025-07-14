package com.vocalInk.feature.voicetotext.model

data class VoiceRecognitionUiState(
    val recognizedText: String? = null,
    val listeningState: RecognitionState = RecognitionState.IDLE,
    val errorText: String? = null,
    val timer: String? = null
)