package com.example.domain.voice.usecase

import com.example.domain.voice.mode.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetVoiceHistoryUseCase @Inject constructor(private val repository: VoiceRepository) {
    operator fun invoke(): Flow<List<VoiceData>> =
        repository.getAllVoices()

}