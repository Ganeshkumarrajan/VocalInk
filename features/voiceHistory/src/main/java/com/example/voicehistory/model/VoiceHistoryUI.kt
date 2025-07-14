package com.example.voicehistory.model

import com.example.core_utils.toFormattedDate
import com.example.domain.voice.model.VoiceData

data class VoiceHistoryUI(
    val dataTime: String,
    val voiceData: String
)

fun VoiceData.toUI(): VoiceHistoryUI {
    return VoiceHistoryUI(
        dataTime = this.timestamp.toFormattedDate(),
        voiceData = this.text
    )
}
