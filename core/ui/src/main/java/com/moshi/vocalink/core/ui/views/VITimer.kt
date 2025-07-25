package com.moshi.vocalink.core.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens

@Composable
fun VITimer(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = VocalInkThemeTokens.typography.headlineLarge,
        color = VocalInkThemeTokens.colors.onSecondaryContainer
    )
}

