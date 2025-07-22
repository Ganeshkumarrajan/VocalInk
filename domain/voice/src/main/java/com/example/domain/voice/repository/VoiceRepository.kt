package com.example.domain.voice.repository

import com.example.domain.voice.model.VoiceData
import kotlinx.coroutines.flow.Flow

interface VoiceRepository {
    suspend fun insertVoice(voiceData: VoiceData)
    fun getAllVoices(): Flow<List<VoiceData>>
}
