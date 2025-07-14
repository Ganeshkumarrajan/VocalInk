package com.example.domain.timer

import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetRemainingTimeUseCase @Inject constructor(private val remainingTimerRepository: RemainingTimeRepository) {
    operator fun invoke(seconds: Int): Flow<Result<Int>> =
        remainingTimerRepository.findRemainingTime(seconds)
}
