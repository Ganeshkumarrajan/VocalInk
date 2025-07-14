package com.example.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.data.voice.model.VoiceEntity

@Database(entities = [VoiceEntity::class], version = 1)
abstract class VoiceDatabase : RoomDatabase() {
    abstract fun voiceDataDao(): VoiceDao
}