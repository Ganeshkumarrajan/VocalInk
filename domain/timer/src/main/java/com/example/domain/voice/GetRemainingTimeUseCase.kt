package com.example.domain.voice

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for starting and observing a countdown timer.
 *
 * @param remainingTimerRepository The repository responsible for generating the countdown flow.
 */
class GetRemainingTimeUseCase @Inject constructor(
    private val remainingTimerRepository: RemainingTimeRepository
) {
    /**
     * Returns a [Flow] that emits the remaining time in seconds every second.
     *
     * @param seconds Total time to count down from.
     * @return A flow emitting [Result<Int>] representing the current second,
     *         or a failure if the timer setup fails.
     */
    operator fun invoke(seconds: Int): Flow<Result<Int>> =
        remainingTimerRepository.findRemainingTime(seconds)
}
