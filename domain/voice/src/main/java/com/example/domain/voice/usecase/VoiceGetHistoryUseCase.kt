package com.example.domain.voice.usecase

import com.example.domain.voice.model.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


/**
 * Use case for retrieving the complete voice history from the repository.
 *
 * @property repository The voice repository that provides access to stored voice data.
 */
class GetVoiceHistoryUseCase @Inject constructor(
    private val repository: VoiceRepository
) {
    /**
     * Returns a flow of all voice entries stored in the repository.
     */
    operator fun invoke(): Flow<List<VoiceData>> =
        repository.getAllVoices()
}
