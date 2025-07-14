package com.example.domain.voice.repository

import com.example.domain.voice.mode.VoiceData
import kotlinx.coroutines.flow.Flow

interface VoiceRepository {
    suspend fun insertVoice(voiceData: VoiceData)
    fun getAllVoices(): Flow<List<VoiceData>>
}
