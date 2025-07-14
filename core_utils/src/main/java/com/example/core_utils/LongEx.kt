package com.example.core_utils

import java.text.SimpleDateFormat
import java.util.*

const val DATE_TIME_SS = "dd MMM yyyy, HH:mm:ss"

fun Long.toFormattedDate(pattern: String = DATE_TIME_SS): String {
    val sdf = SimpleDateFormat(pattern, Locale.getDefault())
    return sdf.format(Date(this))
}