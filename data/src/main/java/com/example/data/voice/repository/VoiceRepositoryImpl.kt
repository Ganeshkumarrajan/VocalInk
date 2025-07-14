package com.example.data.voice.repository

import android.util.Log
import com.example.data.VoiceDao
import com.example.data.voice.model.toDomain
import com.example.data.voice.model.toEntity
import com.example.domain.voice.model.VoiceData
import com.example.domain.voice.repository.VoiceRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.withContext
import javax.inject.Inject

class VoiceRepositoryImpl @Inject constructor(
    private val dao: VoiceDao,
    private val disptacher: CoroutineDispatcher = Dispatchers.IO
) : VoiceRepository {
    override suspend fun insertVoice(voiceData: VoiceData) {
        withContext(disptacher) {
            Log.d("VoiceRepository", "Inserting voice: $voiceData")
            dao.insert(voiceData.toEntity())
        }
    }

    override fun getAllVoices(): Flow<List<VoiceData>> =
        dao.getAll()
            .onEach {
                Log.d("VoiceRepository", "Emitting voice list: $it")
            }
            .map { list ->
                list.map { it.toDomain() }
            }
}