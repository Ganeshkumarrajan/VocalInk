package com.example.domain.voice.usecase

import com.example.domain.voice.model.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import java.util.Date
import javax.inject.Inject

/**
 * Use case for saving a voice-to-text entry into the local repository.
 *
 * @property voiceRepository The repository responsible for persisting voice data.
 */
class SaveVoiceTextUseCase @Inject constructor(private val voiceRepository: VoiceRepository) {

    /**
     * Saves the given [voiceText] with the current timestamp.
     *
     * @param voiceText The transcribed voice text to be saved.
     */
    suspend operator fun invoke(voiceText: String) {
        voiceRepository.insertVoice(VoiceData(text = voiceText, timestamp = Date().time))
    }
}
