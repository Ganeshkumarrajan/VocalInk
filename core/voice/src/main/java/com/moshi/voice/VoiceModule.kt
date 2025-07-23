package com.moshi.voice

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class VoiceModule {
    @Binds
    abstract fun bindVoiceManager(
        impl: VoiceToTextManager
    ): VoiceToTextInterface
}
