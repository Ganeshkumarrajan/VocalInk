package com.vocalInk.feature.voicetotext

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.moshi.vocalink.core.ui.VITimer

@Composable
fun VoiceToTextScreen(voiceViewModel: VoiceViewModel = hiltViewModel()) {

    val uiState by voiceViewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        VoiceToTextState(uiState) {
            voiceViewModel.startListening()
        }
    }
}


@Composable
fun VoiceToTextState(uiState: VoiceRecognitionUiState, onClick: () -> (Unit)) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Button(
            onClick = {
                onClick()
            },
            enabled = uiState.listeningState != RecognitionState.LISTENING
        ) {
            Text("Start")
        }

        uiState.timer?.let {
            VITimer(text = it)
        }
        uiState.errorText?.let {
            Text(text = it, color = Color.Red)
        }

        Text(
            text = uiState.recognizedText ?: "Press the button and speak",
            style = MaterialTheme.typography.bodyLarge
        )

    }
}

@Preview
@Composable
fun PreviewVoiceToTextStateOnIdleState() {
    val state = VoiceRecognitionUiState(
        listeningState = RecognitionState.IDLE
    )

    VoiceToTextState(state) {}
}