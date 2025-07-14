package com.example.voicehistory.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.voicehistory.R
import com.example.voicehistory.viewModel.VoiceHistoryUI
import com.example.voicehistory.viewModel.VoiceHistoryUiState
import com.example.voicehistory.viewModel.VoiceHistoryViewModel
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
fun VoiceHistoryState(uiState: VoiceHistoryUiState<List<VoiceHistoryUI>>, modifier: Modifier) {
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
fun History(voices: List<VoiceHistoryUI>, modifier: Modifier) {
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
fun ShowEmptyList() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        VITitleText(text = stringResource(R.string.empty_list))
    }
}

@Composable
fun ShowError(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        VIError(text = message)
    }
}

@Composable
fun ShowIndicator() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}
