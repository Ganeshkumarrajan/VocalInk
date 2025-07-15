package com.moshi.vocalink.core.ui.views

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.moshi.vocalink.core.ui.theme.VocalInkTheme

@Composable
fun VIError(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        style = VocalInkTheme.typography.headlineMedium,
        color = VocalInkTheme.colors.error,
        textAlign = TextAlign.Center
    )
}
