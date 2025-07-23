package com.example.voicehistory.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.voice.usecase.GetVoiceHistoryUseCase
import com.example.domain.voice.model.VoiceData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.core_utils.toUserMessage
import com.example.voicehistory.mapper.toVoiceUIList
import com.example.voicehistory.model.VoiceHistoryUI
import com.example.voicehistory.model.VoiceHistoryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

/**
 * ViewModel responsible for exposing the voice history UI state.
 *
 * @param getVoiceHistoryUseCase Use case that provides a Flow of [VoiceData] history entries.
 */
@HiltViewModel
class VoiceHistoryViewModel @Inject constructor(getVoiceHistoryUseCase: GetVoiceHistoryUseCase) :
    ViewModel() {

    /**
     * UI state representing the current voice history screen state.
     * Emits:
     * - [VoiceHistoryUiState.Loading] initially
     * - [VoiceHistoryUiState.Success] with transformed UI data
     * - [VoiceHistoryUiState.Error] if an exception is thrown
     */
    val historyState: StateFlow<VoiceHistoryUiState<List<VoiceHistoryUI>>> =
        getVoiceHistoryUseCase.invoke()
            .map<List<VoiceData>, VoiceHistoryUiState<List<VoiceHistoryUI>>> {
                VoiceHistoryUiState.Success(it.toVoiceUIList())
            }.catch { error ->
                emit(VoiceHistoryUiState.Error<Nothing>(error.toUserMessage()))
            }.stateIn(
                scope = viewModelScope,
                initialValue = VoiceHistoryUiState.Loading,
                started = SharingStarted.WhileSubscribed(3000)
            )
}
