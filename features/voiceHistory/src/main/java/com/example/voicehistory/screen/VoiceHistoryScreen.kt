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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.voicehistory.R
import com.example.voicehistory.model.VoiceHistoryUI
import com.example.voicehistory.model.VoiceHistoryUiState
import com.example.voicehistory.viewModel.VoiceHistoryViewModel
import com.moshi.vocalink.core.ui.components.VILoading
import com.moshi.vocalink.core.ui.components.VoiceEntryCard
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens
import com.moshi.vocalink.core.ui.views.VIError
import com.moshi.vocalink.core.ui.views.VITitleText

/**
 * Displays the main Voice History screen with a FAB to add a new voice entry.
 *
 * @param historyViewModel ViewModel providing the state of voice history list.
 * @param onAddVoice Callback triggered when the FAB is clicked.
 */
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
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_voice)
                )
            }
        }
    ) { innerPadding ->
        VoiceHistoryState(
            modifier = Modifier.padding(innerPadding), uiState = uiState
        )
    }
}

/**
 * Renders the appropriate UI based on the current [uiState] of the voice history screen.
 */
@Composable
internal fun VoiceHistoryState(
    uiState: VoiceHistoryUiState<List<VoiceHistoryUI>>,
    modifier: Modifier
) = when (uiState) {
    is VoiceHistoryUiState.Success -> {
        if (uiState.data.isEmpty()) VoiceHistoryEmptyState()
        else History(uiState.data, modifier)
    }

    is VoiceHistoryUiState.Error -> VoiceHistoryErrorState(uiState.errorMessage)
    is VoiceHistoryUiState.Loading -> VoiceHistoryLoadingState()
}

/**
 * Renders a list of voice history entries using [LazyColumn].
 */
@Composable
private fun History(voices: List<VoiceHistoryUI>, modifier: Modifier) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(
            horizontal = VocalInkThemeTokens.dimens.ScreenPadding,
            vertical = VocalInkThemeTokens.dimens.Small
        ),
        verticalArrangement = Arrangement.spacedBy(VocalInkThemeTokens.dimens.ScreenPadding)
    ) {
        items(voices) { item ->
            VoiceEntryCard(
                timestamp = item.dataTime,
                content = item.voiceData
            )
        }
    }
}

/** Shown when the voice history list is empty. */
@Composable
private fun VoiceHistoryEmptyState() {
    Box(
        modifier = Modifier
            .padding(VocalInkThemeTokens.dimens.ScreenPadding)
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        VITitleText(text = stringResource(R.string.empty_list))
    }
}

/** Shown when the voice history screen encounters an error. */
@Composable
private fun VoiceHistoryErrorState(message: String) {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        VIError(text = message)
    }
}

/** Shown while voice history is being loaded. */
@Composable
private fun VoiceHistoryLoadingState() {
    VILoading()
}
