package com.moshi.vocalink.di

import android.content.Context
import androidx.room.Room
import com.moshi.vocalink.local.VoiceDao
import com.moshi.vocalink.local.VoiceDatabase

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object VoiceDatabaseModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): VoiceDatabase {
        return Room.databaseBuilder(
            context,
            VoiceDatabase::class.java,
            "voice_db"
        ).build()
    }

    @Provides
    fun provideVoiceDao(db: VoiceDatabase): VoiceDao = db.voiceDataDao()

}