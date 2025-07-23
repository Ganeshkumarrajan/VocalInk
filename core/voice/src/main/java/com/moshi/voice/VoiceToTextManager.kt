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

/**
 * Manages speech-to-text functionality using Android's [SpeechRecognizer].
 *
 * This class sets up and controls voice recognition, handling both results and errors.
 * It implements [VoiceToTextInterface] to provide an abstracted contract.
 *
 * @param context Application context used to initialize the [SpeechRecognizer].
 */
class VoiceToTextManager @Inject constructor(
    @ApplicationContext private val context: Context
) : VoiceToTextInterface {

    private var speechRecognizer: SpeechRecognizer? = null
    private var recognizerIntent: Intent? = null

    /**
     * Starts the voice recognition process.
     *
     * @param onResult Callback with recognized text when successful.
     * @param onError Callback with user-friendly error message.
     * @param onEd Callback when speech input ends.
     */
    override fun startListening(
        onResult: (String) -> Unit,
        onError: (String) -> Unit,
        onEnd: () -> Unit
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
                    onEnd()
                }

                override fun onError(error: Int) {
                    val errorMsg = when (error) {
                        SpeechRecognizer.ERROR_NO_MATCH ->
                            context.getString(R.string.did_not_catch_try_again)

                        SpeechRecognizer.ERROR_SPEECH_TIMEOUT ->
                            context.getString(R.string.no_speech_input_try_again)

                        SpeechRecognizer.ERROR_NETWORK ->
                            context.getString(R.string.network_issue)

                        SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS ->
                            context.getString(R.string.microphone_permission_required)

                        else ->
                            "${context.getString(R.string.error_occurred)} $error"
                    }

                    onError(errorMsg)
                }

                override fun onResults(results: Bundle?) {
                    val matches = results
                        ?.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                    onResult(matches?.firstOrNull() ?: "")
                }

                override fun onPartialResults(partialResults: Bundle?) {}
                override fun onEvent(eventType: Int, params: Bundle?) {}
            })
        }

        recognizerIntent?.let {
            speechRecognizer?.startListening(it)
        }
    }

    /**
     * Releases resources held by the speech recognizer.
     */
    override fun destroy() {
        speechRecognizer?.destroy()
        speechRecognizer = null
    }
}
