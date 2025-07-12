package com.moshi.voice

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import dagger.hilt.android.qualifiers.ApplicationContext
import java.util.Locale
import javax.inject.Inject

class VoiceToTextManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    fun startListening(
        onResult: (String) -> Unit, onError: (String) -> Unit,
        onEd: () -> (Unit)
    ) {
        speechRecognizer?.destroy()
        if (speechRecognizer == null) {
            speechRecognizer = SpeechRecognizer.createSpeechRecognizer(context)
            recognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH).apply {
                putExtra(
                    RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
                )
                putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
                putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, true)
            }

            speechRecognizer?.setRecognitionListener(object : RecognitionListener {
                override fun onReadyForSpeech(params: Bundle?) {}
                override fun onBeginningOfSpeech() {}
                override fun onRmsChanged(rmsdB: Float) {}
                override fun onBufferReceived(buffer: ByteArray?) {}
                override fun onEndOfSpeech() {
                    onEd()
                }

                override fun onError(error: Int) {

                    val error = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH -> context.getString(R.string.did_not_catch_try_again)
                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT -> context.getString(R.string.no_speech_input_try_again)
                        SpeechRecognizer.ERROR_NETWORK -> context.getString(R.string.network_issue)
                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS -> context.getString(R.string.microphone_permission_required)
                        else -> "${context.getString(R.string.error_occurred)} $error"
                    }

                    onError(error)
                }

                override fun onResults(results: Bundle?) {
                    val matches = results?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    onResult(matches?.firstOrNull() ?: "")
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        speechRecognizer?.startListening(recognizerIntent)
    }

    fun destroy() {
        speechRecognizer?.destroy()
    }
}