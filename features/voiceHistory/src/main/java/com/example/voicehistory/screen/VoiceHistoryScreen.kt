package com.example.voicehistory.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.voicehistory.R
import com.example.voicehistory.model.VoiceHistoryUI
import com.example.voicehistory.model.VoiceHistoryUiState
import com.example.voicehistory.viewModel.VoiceHistoryViewModel
import com.moshi.vocalink.core.ui.components.VILoading
import com.moshi.vocalink.core.ui.components.VoiceEntryCard
import com.moshi.vocalink.core.ui.theme.VocalInkTheme
import com.moshi.vocalink.core.ui.views.VIError
import com.moshi.vocalink.core.ui.views.VITitleText

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VoiceHistoryScreen(
    historyViewModel: VoiceHistoryViewModel = hiltViewModel(),
    onAddVoice: () -> Unit
) {
    val uiState by historyViewModel.historyState.collectAsStateWithLifecycle()

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = onAddVoice) {
                Icon(Icons.Default.Add, contentDescription = "Add")
            }
        }
    ) { innerPadding ->
        VoiceHistoryState(
            modifier = Modifier.padding(innerPadding), uiState = uiState
        )
    }
}


@Composable
private fun VoiceHistoryState(
    uiState: VoiceHistoryUiState<List<VoiceHistoryUI>>,
    modifier: Modifier
) {
    when (uiState) {
        is VoiceHistoryUiState.Success -> {
            if (uiState.data.isEmpty()) {
                ShowEmptyList()
            } else {
                History(uiState.data, modifier)
            }
        }

        is VoiceHistoryUiState.Error -> {
            ShowError(uiState.errorMessage)
        }

        is VoiceHistoryUiState.Loading -> {
            ShowIndicator()
        }
    }
}

@Composable
private fun History(voices: List<VoiceHistoryUI>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = VocalInkTheme.dimens.ScreenPadding,
            vertical = VocalInkTheme.dimens.Small
        ),
        verticalArrangement = Arrangement.spacedBy(VocalInkTheme.dimens.ScreenPadding)
    ) {
        items(voices) { item ->
            VoiceEntryCard(
                timestamp = item.dataTime,
                content = item.voiceData
            )
        }
    }
}

@Composable
private fun ShowEmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        VITitleText(text = stringResource(R.string.empty_list))
    }
}

@Composable
private fun ShowError(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        VIError(text = message)
    }
}

@Composable
private fun ShowIndicator() {
    VILoading()
}


@Preview(showBackground = true)
@Composable
fun PreviewVoiceHistoryScreen() {
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

@Preview(showBackground = true)
@Composable
fun PreviewEmptyList() {
    VoiceHistoryState(
        uiState = VoiceHistoryUiState.Success(emptyList()),
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewLoading() {
    VoiceHistoryState(
        uiState = VoiceHistoryUiState.Loading,
        modifier = Modifier
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewError() {
    VoiceHistoryState(
        uiState = VoiceHistoryUiState.Error("Failed to load voice history."),
        modifier = Modifier
    )
}
