package com.example.core_utils

fun Throwable.toUserMessage(): String =
    localizedMessage.takeIf { !it.isNullOrBlank() } ?: "Something went wrong"
