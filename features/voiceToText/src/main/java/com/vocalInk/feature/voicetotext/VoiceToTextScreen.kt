package com.vocalInk.feature.voicetotext

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moshi.vocalink.core.ui.VIError
import com.moshi.vocalink.core.ui.VIListeningIndicator
import com.moshi.vocalink.core.ui.VITimer
import com.moshi.vocalink.core.ui.VITitleText

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun VoiceToTextScreen(voiceViewModel: VoiceViewModel = hiltViewModel()) {

    val uiState by voiceViewModel.uiState.collectAsStateWithLifecycle()
    DisposableEffect(Unit) {
        onDispose {
            voiceViewModel.stopListening()
        }
    }

    VoiceToTextState(uiState) {
        voiceViewModel.startListening()
    }

}

@Composable
fun VoiceToTextState(uiState: VoiceRecognitionUiState, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (uiState.listeningState) {
            RecognitionState.LISTENING -> {
                VIListeningIndicator()
                VITimer(
                    text = uiState.timer ?: ""
                )
            }

            RecognitionState.FINISHED -> {
                VITitleText(
                    uiState.recognizedText.takeIf { !it.isNullOrEmpty() }
                        ?: stringResource(R.string.did_not_catch_try_again)
                )
            }

            RecognitionState.ERROR -> {
                ShowButton { onClick() }
                VIError(text = uiState.errorText ?: "")
            }

            RecognitionState.IDLE -> {
                ShowButton { onClick() }
            }

        }
    }
}


@Composable
fun ShowButton(onClick: () -> Unit) {
    Button(
        onClick = {
            onClick()
        }) {
        Text("Start")
    }
}


@Preview(showBackground = true)
@Composable
fun VoiceToTextPreview(
    @PreviewParameter(VoiceUiStatePreviewProvider::class)
    uiState: VoiceRecognitionUiState
) {
    MaterialTheme {
        VoiceToTextState(uiState = uiState, onClick = {})
    }
}

class VoiceUiStatePreviewProvider : PreviewParameterProvider<VoiceRecognitionUiState> {
    override val values = sequenceOf(
        // Idle state
        VoiceRecognitionUiState(
            listeningState = RecognitionState.IDLE
        ),
        // Listening state
        VoiceRecognitionUiState(
            listeningState = RecognitionState.LISTENING,
            timer = "0:15"
        ),
        // Error state
        VoiceRecognitionUiState(
            listeningState = RecognitionState.ERROR,
            errorText = "Microphone not available"
        ),
        // Finished state
        VoiceRecognitionUiState(
            listeningState = RecognitionState.FINISHED,
            recognizedText = "Test voice input"
        )
    )
}