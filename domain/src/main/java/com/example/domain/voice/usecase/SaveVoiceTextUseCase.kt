package com.example.domain.voice.usecase

import com.example.domain.voice.mode.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import java.util.Date
import javax.inject.Inject

class SaveVoiceTextUseCase @Inject constructor(private val voiceRepository: VoiceRepository) {
    suspend operator fun invoke(voiceText: String) {
        voiceRepository.insertVoice(VoiceData(text = voiceText, timestamp = Date().time))
    }
}