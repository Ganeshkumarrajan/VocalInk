package com.moshi.vocalink.repository

import com.moshi.vocalink.local.VoiceDao
import com.moshi.vocalink.model.toDomain
import com.moshi.vocalink.model.toEntity
import com.example.domain.voice.model.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VoiceRepositoryImpl @Inject constructor(
    private val dao: VoiceDao,
    private val dispatcher: CoroutineDispatcher
) : VoiceRepository {

    override suspend fun insertVoice(voiceData: VoiceData) {
        withContext(dispatcher) {
            dao.insert(voiceData.toEntity())
        }
    }

    override fun getAllVoices(): Flow<List<VoiceData>> =
        dao.getAll()
            .map { list ->
                list.map { it.toDomain() }
            }
}
