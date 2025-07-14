package com.example.data.voice.di

import android.content.Context
import androidx.room.Room
import com.example.data.VoiceDao
import com.example.data.VoiceDatabase
import com.example.data.timer.RemainingTimeRepositoryImpl
import com.example.data.voice.repository.VoiceRepositoryImpl
import com.example.domain.timer.RemainingTimeRepository
import com.example.domain.voice.repository.VoiceRepository
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

    @Provides
    fun provideVoiceHistoryRepository(dao: VoiceDao): VoiceRepository =
        VoiceRepositoryImpl(dao)

    @Provides
    fun provideRemainingTimeRepository(): RemainingTimeRepository =
        RemainingTimeRepositoryImpl()

}