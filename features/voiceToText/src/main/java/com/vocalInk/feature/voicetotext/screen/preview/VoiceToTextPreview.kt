package com.vocalInk.feature.voicetotext.screen.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.moshi.vocalink.core.ui.theme.VocalInkTheme
import com.vocalInk.feature.voicetotext.model.RecognitionState
import com.vocalInk.feature.voicetotext.model.VoiceRecognitionUiState
import com.vocalInk.feature.voicetotext.screen.VoiceToTextState

@Preview(showBackground = true, name = "Idle")
@Composable
fun PreviewIdle() {
    VocalInkTheme {
        VoiceToTextState(
            uiState = VoiceRecognitionUiState(listeningState = RecognitionState.IDLE),
            onClick = {},
            onSaveClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Listening")
@Composable
fun PreviewListening() {
    VocalInkTheme {
        VoiceToTextState(
            uiState = VoiceRecognitionUiState(
                listeningState = RecognitionState.LISTENING,
                timer = "0:15"
            ),
            onClick = {},
            onSaveClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Error")
@Composable
fun PreviewError() {
    VocalInkTheme {
        VoiceToTextState(
            uiState = VoiceRecognitionUiState(
                listeningState = RecognitionState.ERROR,
                errorText = "Microphone not available"
            ),
            onClick = {},
            onSaveClicked = {}
        )
    }
}

@Preview(showBackground = true, name = "Finished")
@Composable
fun PreviewFinished() {
    VocalInkTheme {
        VoiceToTextState(
            uiState = VoiceRecognitionUiState(
                listeningState = RecognitionState.FINISHED,
                recognizedText = "Test voice input"
            ),
            onClick = {},
            onSaveClicked = {}
        )
    }
}
