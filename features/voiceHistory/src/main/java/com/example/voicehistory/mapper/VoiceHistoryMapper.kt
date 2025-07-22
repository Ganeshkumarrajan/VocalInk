package com.example.voicehistory.mapper

import com.example.core_utils.toFormattedDate
import com.example.domain.voice.model.VoiceData
import com.example.voicehistory.model.VoiceHistoryUI

fun List<VoiceData>.toVoiceUIList(): List<VoiceHistoryUI> {
    return map { it.toUI() }
}

fun VoiceData.toUI(): VoiceHistoryUI {
    return VoiceHistoryUI(
        dataTime = this.timestamp.toFormattedDate(),
        voiceData = this.text
    )
}
