package com.example.domain.timer

import kotlinx.coroutines.flow.Flow

interface RemainingTimeRepository {
    fun findRemainingTime(seconds: Int): Flow<Result<Int>>
}