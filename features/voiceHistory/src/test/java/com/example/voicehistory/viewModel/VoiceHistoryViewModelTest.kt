package com.example.voicehistory.viewModel

import app.cash.turbine.test
import com.example.domain.voice.model.VoiceData
import com.example.domain.voice.usecase.GetVoiceHistoryUseCase
import com.example.voicehistory.mapper.toUI
import com.example.voicehistory.model.VoiceHistoryUiState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

@ExperimentalCoroutinesApi
class VoiceHistoryViewModelTest {

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var getVoiceHistoryUseCase: GetVoiceHistoryUseCase
    private lateinit var viewModel: VoiceHistoryViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        getVoiceHistoryUseCase = mockk()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `historyState emits Success when useCase returns data`() = runTest {
        val fakeData = listOf(
            VoiceData(text = "Hello", timestamp = 1720932323000),
            VoiceData(text = "World", timestamp = 1720932350000)
        )
        val expectedUI = fakeData.map { it.toUI() }

        // Mock use case
        every { getVoiceHistoryUseCase.invoke() } returns flowOf(fakeData)

        viewModel = VoiceHistoryViewModel(getVoiceHistoryUseCase)

        viewModel.historyState.test {
            assertEquals(VoiceHistoryUiState.Loading, awaitItem())
            assertEquals(VoiceHistoryUiState.Success(expectedUI), awaitItem())
            cancelAndConsumeRemainingEvents()
        }
    }

    @Test
    fun `historyState emits Error when useCase throws`() = runTest {
        val errorMessage = "DB error"

        every { getVoiceHistoryUseCase.invoke() } returns flow {
            throw RuntimeException(errorMessage)
        }

        viewModel = VoiceHistoryViewModel(getVoiceHistoryUseCase)

        viewModel.historyState.test {
            assertEquals(VoiceHistoryUiState.Loading, awaitItem())
            val result = awaitItem()
            assertTrue(result is VoiceHistoryUiState.Error)
            assertEquals(errorMessage, (result as VoiceHistoryUiState.Error).errorMessage)
            cancelAndConsumeRemainingEvents()
        }
    }
}
