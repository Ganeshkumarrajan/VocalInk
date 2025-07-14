package com.example.voicehistory.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.domain.voice.usecase.GetVoiceHistoryUseCase
import com.example.domain.voice.mode.VoiceData
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import androidx.lifecycle.viewModelScope
import com.example.core_utils.toFormattedDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class VoiceHistoryViewModel @Inject constructor(private val getVoiceHistoryUseCase: GetVoiceHistoryUseCase) :
    ViewModel() {

    val historyState: StateFlow<VoiceHistoryUiState<List<VoiceHistoryUI>>> =
        getVoiceHistoryUseCase.invoke()
            .onEach {
                Log.d("VoiceHistoryViewModel", "Received voices: $it")
            }
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

data class VoiceHistoryUI(
    val dataTime: String,
    val voiceData: String
)

fun VoiceData.toUI(): VoiceHistoryUI {
    return VoiceHistoryUI(
        dataTime = this.timestamp.toFormattedDate(),
        voiceData = this.text
    )
}


sealed class VoiceHistoryUiState<out T> {
    data class Success<T>(val data: T) : VoiceHistoryUiState<T>()
    data class Error<Nothing>(val errorMessage: String) : VoiceHistoryUiState<Nothing>()
    object Loading : VoiceHistoryUiState<Nothing>()
}
