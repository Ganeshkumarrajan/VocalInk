package com.moshi.vocalink.core.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable

object VocalInkThemeTokens {
    val dimens: Dimens
        get() = Dimens

    val colors: ColorScheme
        @Composable get() = MaterialTheme.colorScheme

    val typography: Typography
        @Composable get() = MaterialTheme.typography
}

@Composable
fun VocalInkTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        content = content
    )
}
