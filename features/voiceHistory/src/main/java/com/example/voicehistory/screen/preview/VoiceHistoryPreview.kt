package com.example.voicehistory.screen.preview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.voicehistory.model.VoiceHistoryUI
import com.example.voicehistory.model.VoiceHistoryUiState
import com.example.voicehistory.screen.VoiceHistoryState
import com.moshi.vocalink.core.ui.theme.VocalInkTheme

@Preview(showBackground = true, name = "Empty State")
@Composable
fun PreviewEmptyList() {
    VocalInkTheme {
        VoiceHistoryState(
            uiState = VoiceHistoryUiState.Success(emptyList()),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Loading State")
@Composable
fun PreviewLoading() {
    VocalInkTheme {
        VoiceHistoryState(
            uiState = VoiceHistoryUiState.Loading,
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Error State")
@Composable
fun PreviewError() {
    VocalInkTheme {
        VoiceHistoryState(
            uiState = VoiceHistoryUiState.Error("Failed to load voice history."),
            modifier = Modifier
        )
    }
}

@Preview(showBackground = true, name = "Success List")
@Composable
fun PreviewVoiceHistoryScreen() {
    VocalInkTheme {
        VoiceHistoryState(
            uiState = VoiceHistoryUiState.Success(
                listOf(
                    VoiceHistoryUI("14 Jul 2025, 10:15 AM", "Hello world"),
                    VoiceHistoryUI("14 Jul 2025, 11:00 AM", "Another note")
                )
            ),
            modifier = Modifier
        )
    }
}