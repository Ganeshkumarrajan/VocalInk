package com.moshi.vocalink.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.moshi.vocalink.model.VoiceEntity

@Database(entities = [VoiceEntity::class], version = 1)
abstract class VoiceDatabase : RoomDatabase() {
    abstract fun voiceDataDao(): VoiceDao
}