package com.example.data.voice.di

import com.example.data.VoiceDao
import com.example.data.timer.RemainingTimeRepositoryImpl
import com.example.data.voice.repository.VoiceRepositoryImpl
import com.example.domain.timer.RemainingTimeRepository
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

    @Provides
    fun provideRemainingTimeRepository(@IoDispatcher dispatcher: CoroutineDispatcher): RemainingTimeRepository =
        RemainingTimeRepositoryImpl(dispatcher)
}
