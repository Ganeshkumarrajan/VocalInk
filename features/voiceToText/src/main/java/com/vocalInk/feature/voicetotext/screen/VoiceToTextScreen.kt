package com.vocalInk.feature.voicetotext.screen

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
import androidx.compose.runtime.LaunchedEffect
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
import com.moshi.vocalink.core.ui.views.VIError
import com.moshi.vocalink.core.ui.components.VIListeningIndicator
import com.moshi.vocalink.core.ui.theme.VocalInkTheme
import com.moshi.vocalink.core.ui.views.VIButton
import com.moshi.vocalink.core.ui.views.VITimer
import com.moshi.vocalink.core.ui.views.VITitleText
import com.vocalInk.feature.voicetotext.R

import com.vocalInk.feature.voicetotext.model.RecognitionState
import com.vocalInk.feature.voicetotext.model.VoiceRecognitionUiState
import com.vocalInk.feature.voicetotext.viewModel.VoiceViewModel

@Composable
fun VoiceToTextScreen(voiceViewModel: VoiceViewModel = hiltViewModel(), onFinish: () -> Unit) {

    val uiState by voiceViewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        voiceViewModel.closeOnSave.collect { shouldClose ->
            if (shouldClose) {
                onFinish()
            }
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            voiceViewModel.stopListening()
        }
    }

    VoiceToTextState(uiState, {
        voiceViewModel.startListening()
    }, {
        voiceViewModel.onSave(it)
    })
}

@Composable
fun VoiceToTextState(
    uiState: VoiceRecognitionUiState,
    onClick: () -> Unit,
    onSaveClicked: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .background(color = MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(VocalInkTheme.dimens.ScreenPadding),
        verticalArrangement = Arrangement.spacedBy(VocalInkTheme.dimens.Small),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        when (uiState.listeningState) {
            RecognitionState.ERROR -> {
                ShowButton { onClick() }
                VIError(text = uiState.errorText ?: "")
            }

            RecognitionState.LISTENING -> {
                VIListeningIndicator()
                VITimer(text = uiState.timer ?: "")
            }

            RecognitionState.FINISHED -> {
                uiState.recognizedText
                    ?.takeIf { it.isNotBlank() }
                    ?.let { text ->
                        VITitleText(text = text)
                        ShowSaveButton { onSaveClicked(text) }
                    }
                    ?: ""
            }

            else -> {
                ShowButton { onClick() }
            }
        }
    }
}


@Composable
fun ShowButton(onClick: () -> Unit) {
    VIButton(
        onClick = onClick,
        text = stringResource(R.string.start)
    )
}

@Composable
fun ShowSaveButton(onClick: () -> Unit) {
    VIButton(
        onClick = onClick,
        text = stringResource(R.string.save)
    )
}

@Preview(showBackground = true)
@Composable
fun VoiceToTextPreview(
    @PreviewParameter(VoiceUiStatePreviewProvider::class)
    uiState: VoiceRecognitionUiState
) {
    MaterialTheme {
        VoiceToTextState(uiState = uiState, onClick = {}, {})
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