package com.vocalInk.feature.voicetotext.viewModel

import app.cash.turbine.turbineScope
import com.example.domain.voice.usecase.SaveVoiceTextUseCase
import com.moshi.voice.VoiceToTextInterface
import com.vocalInk.feature.voicetotext.model.RecognitionState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import kotlin.test.Test


@ExperimentalCoroutinesApi
class VoiceViewModelTest {

    private lateinit var viewModel: VoiceViewModel
    private val voiceToTextManager = mockk<VoiceToTextInterface>(relaxed = true)
    private val getRemainingTimeUseCase = mockk<GetRemainingTimeUseCase>()
    private val saveVoiceTextUseCase = mockk<SaveVoiceTextUseCase>(relaxed = true)

    @Before
    fun setup() {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        viewModel = VoiceViewModel(
            voiceToTextManager,
            getRemainingTimeUseCase,
            saveVoiceTextUseCase
        )
    }

    @Test
    fun `onSave emits close event`() = runTest {
        turbineScope {
            val turbine = viewModel.closeOnSave.testIn(this)

            viewModel.onSave("Test speech")

            advanceUntilIdle()

            assertEquals(true, turbine.awaitItem())
            turbine.cancel()
        }
    }

    @Test
    fun `startListening updates state to LISTENING`() {
        every {
            voiceToTextManager.startListening(any(), any(), any())
        } answers {
            val onResult = arg<(String) -> Unit>(0)
            onResult("Test result")
        }

        viewModel.startListening()

        val state = viewModel.uiState.value
        assertEquals("Test result", state.recognizedText)
        assertEquals(RecognitionState.FINISHED, state.listeningState)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }
}
