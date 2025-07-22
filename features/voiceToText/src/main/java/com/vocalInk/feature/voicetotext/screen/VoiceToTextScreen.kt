package com.vocalInk.feature.voicetotext.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.moshi.vocalink.core.ui.views.VIError
import com.moshi.vocalink.core.ui.components.VIListeningIndicator
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens
import com.moshi.vocalink.core.ui.views.VIButton
import com.moshi.vocalink.core.ui.views.VITimer
import com.moshi.vocalink.core.ui.views.VITitleText
import com.vocalInk.feature.voicetotext.R
import com.vocalInk.feature.voicetotext.model.RecognitionState
import com.vocalInk.feature.voicetotext.model.VoiceRecognitionUiState
import com.vocalInk.feature.voicetotext.viewModel.VoiceViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@Composable
fun VoiceToTextScreen(
    voiceViewModel: VoiceViewModel = hiltViewModel(),
    onFinish: () -> Unit
) {
    val uiState by voiceViewModel.uiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(Unit) {
        val job = lifecycleOwner.lifecycleScope.launch {
            lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                voiceViewModel.closeOnSave.collectLatest { shouldClose ->
                    if (shouldClose) onFinish()
                }
            }
        }

        onDispose {
            job.cancel()
            voiceViewModel.stopListening()
        }
    }

    VoiceToTextState(
        uiState = uiState,
        onClick = voiceViewModel::startListening,
        onSaveClicked = voiceViewModel::onSave
    )
}

@Composable
fun VoiceToTextState(
    uiState: VoiceRecognitionUiState,
    onClick: () -> Unit,
    onSaveClicked: (String) -> Unit
) {
    when (uiState.listeningState) {
        RecognitionState.ERROR -> {
            ErrorState(uiState.errorText, onRetry = onClick)
        }

        RecognitionState.LISTENING -> {
            ListeningState(timer = uiState.timer)
        }

        RecognitionState.FINISHED -> {
            val text = uiState.recognizedText.orEmpty()
            if (text.isNotBlank()) {
                FinishedState(text = text, onSave = { onSaveClicked(text) })
            } else {
                ShowButton(onClick)
            }
        }
        RecognitionState.IDLE -> {
            ShowButton(onClick)
        }
    }
}

@Composable
fun ShowButton(onClick: () -> Unit) {
    BaseStateContainer {
        VIButton(
            onClick = onClick,
            text = stringResource(R.string.start)
        )
    }
}

@Composable
fun ErrorState(errorText: String?, onRetry: () -> Unit) {
    BaseStateContainer {
        VIButton(onClick = onRetry, text = stringResource(R.string.start))
        VIError(text = errorText ?: stringResource(R.string.unknown_error))
    }
}

@Composable
fun ListeningState(timer: String?) {
    BaseStateContainer {
        VIListeningIndicator()
        VITimer(text = timer ?: "0:00")
    }
}

@Composable
fun FinishedState(text: String, onSave: () -> Unit) {
    BaseStateContainer {
        VITitleText(text = text)
        VIButton(onClick = onSave, text = stringResource(R.string.save))
    }
}

@Composable
fun BaseStateContainer(content: @Composable ColumnScope.() -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(VocalInkThemeTokens.dimens.Small),
        modifier = Modifier
            .fillMaxSize()
            .padding(VocalInkThemeTokens.dimens.ScreenPadding),
        content = content
    )
}
