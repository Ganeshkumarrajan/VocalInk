package com.moshi.vocalink.core.ui.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable

object VocalInkTheme {
    val colors: ColorScheme
        @Composable
        get() = MaterialTheme.colorScheme

    val typography: androidx.compose.material3.Typography
        @Composable
        get() = MaterialTheme.typography

    val dimens: Dimens
        get() = Dimens
}
