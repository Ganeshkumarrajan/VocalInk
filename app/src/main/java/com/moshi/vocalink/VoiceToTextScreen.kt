package com.moshi.vocalink

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Locale

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
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        Text(
            text = uiState.recognizedText ?: "Press the button and speak",
            style = MaterialTheme.typography.bodyLarge
        )

        uiState.errorText?.let {
            Text(text = it, color = Color.Red)
        }

        Button(
            onClick = {
                onClick()
            },
            enabled = uiState.listeningState != RecognitionState.LISTENING
        ) {
            Text(stringResource(R.string.start_listening))
        }
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