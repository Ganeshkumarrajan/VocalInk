package com.example.voicehistory.viewModel

import androidx.lifecycle.ViewModel
import com.example.domain.voice.usecase.GetVoiceHistoryUseCase
import com.example.domain.voice.model.VoiceData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.voicehistory.model.VoiceHistoryUI
import com.example.voicehistory.model.VoiceHistoryUiState
import com.example.voicehistory.model.toUI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import javax.inject.Inject

@HiltViewModel
class VoiceHistoryViewModel @Inject constructor(private val getVoiceHistoryUseCase: GetVoiceHistoryUseCase) :
    ViewModel() {

    val historyState: StateFlow<VoiceHistoryUiState<List<VoiceHistoryUI>>> =
        getVoiceHistoryUseCase.invoke()
            .map<List<VoiceData>, VoiceHistoryUiState<List<VoiceHistoryUI>>> {
                val result = it.map { voice ->
                    voice.toUI()
                }
                VoiceHistoryUiState.Success(result)
            }.catch { error ->
                emit(VoiceHistoryUiState.Error<Nothing>(error.localizedMessage ?: ""))
            }.stateIn(
                scope = viewModelScope,
                initialValue = VoiceHistoryUiState.Loading,
                started = SharingStarted.Eagerly
            )
}
