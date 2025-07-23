package com.moshi.voice

interface VoiceToTextInterface {

    fun startListening(
        onResult: (String) -> Unit,
        onError: (String) -> Unit,
        onEnd: () -> Unit
    )

    fun destroy()

}