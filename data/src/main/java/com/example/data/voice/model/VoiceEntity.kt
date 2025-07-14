package com.example.data.voice.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.voice.model.VoiceData

@Entity(tableName = "voice_data")
data class VoiceEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val text: String,
    val timestamp: Long
)

fun VoiceEntity.toDomain() = VoiceData(id, text, timestamp)
fun VoiceData.toEntity() = VoiceEntity(id, text, timestamp)