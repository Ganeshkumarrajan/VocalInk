package com.moshi.vocalink.di

import com.moshi.vocalink.local.VoiceDao

import com.moshi.vocalink.repository.VoiceRepositoryImpl
import com.example.domain.voice.repository.VoiceRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideVoiceHistoryRepository(
        dao: VoiceDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): VoiceRepository =
        VoiceRepositoryImpl(dao, dispatcher)
}
