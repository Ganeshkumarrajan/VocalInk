package com.moshi.vocalink.core.ui.views

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moshi.vocalink.core.ui.theme.VocalInkTheme
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens

@Composable
fun VIButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = VocalInkThemeTokens.dimens.ScreenPadding),
        shape = RoundedCornerShape(VocalInkThemeTokens.dimens.CardCornerRadius)
    ) {
        Text(
            text = text,
            style = VocalInkThemeTokens.typography.bodyLarge,
            color = VocalInkThemeTokens.colors.onPrimary
        )
    }
}