package com.example.voicehistory.model

import androidx.compose.runtime.Immutable

/**
 * UI model representing a single voice history entry displayed in the list.
 *
 * @property dataTime A human-readable string representing when the voice entry was recorded.
 * @property voiceData The transcribed text or data from the voice input.
 */
@Immutable
data class VoiceHistoryUI(
    val dataTime: String,
    val voiceData: String
)
