package com.example.data.timer

import com.example.domain.voice.RemainingTimeRepository
import kotlinx.coroutines.CoroutineDispatcher

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class RemainingTimeRepositoryImpl(private val dispatcher: CoroutineDispatcher) :
    RemainingTimeRepository {
    override fun findRemainingTime(seconds: Int): Flow<Result<Int>> = flow {
        var current = seconds
        while (current >= 0) {
            emit(Result.success(current))
            delay(1000)
            current--
        }
    }.flowOn(dispatcher)
}
