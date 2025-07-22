package com.moshi.vocalink.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.moshi.vocalink.model.VoiceEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface VoiceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(voice: VoiceEntity)

    @Query("SELECT * FROM voice_data ORDER BY timestamp DESC")
    fun getAll(): Flow<List<VoiceEntity>>
}
