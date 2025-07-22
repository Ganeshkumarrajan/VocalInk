package com.example.domain.voice

import kotlinx.coroutines.flow.Flow

interface RemainingTimeRepository {
    fun findRemainingTime(seconds: Int): Flow<Result<Int>>
}
