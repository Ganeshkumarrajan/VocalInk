package com.example.voicehistory.model

sealed class VoiceHistoryUiState<out T> {
    data class Success<T>(val data: T) : VoiceHistoryUiState<T>()
    data class Error<Nothing>(val errorMessage: String) : VoiceHistoryUiState<Nothing>()
    object Loading : VoiceHistoryUiState<Nothing>()
}
