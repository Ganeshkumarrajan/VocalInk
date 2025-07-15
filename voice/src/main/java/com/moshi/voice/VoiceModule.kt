package com.moshi.voice

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object VoiceModule {
    @Provides
    @Singleton
    fun provideVoiceManager(
        @ApplicationContext context: Context
    ): VoiceToTextInterface {
        return VoiceToTextManager(context)
    }
}