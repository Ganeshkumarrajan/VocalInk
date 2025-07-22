package com.moshi.vocalink.core.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens

@Composable
fun VIError(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = VocalInkThemeTokens.typography.headlineMedium,
        color = VocalInkThemeTokens.colors.error,
        textAlign = TextAlign.Center
    )
}
