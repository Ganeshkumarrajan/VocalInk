package com.moshi.voice

import android.content.Context
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // or ViewModelComponent if needed
abstract class VoiceModule {
    @Binds
    abstract fun bindVoiceManager(
        impl: VoiceToTextManager
    ): VoiceToTextInterface
}
