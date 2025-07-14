package com.moshi.vocalink.core.ui.views

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.moshi.vocalink.core.ui.theme.VocalInkTheme

@Composable
fun VIButton(
    modifier: Modifier = Modifier,
    text: String,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier
            .padding(horizontal = VocalInkTheme.dimens.ScreenPadding),
        shape = RoundedCornerShape(VocalInkTheme.dimens.CardCornerRadius)
    ) {
        Text(
            text = text,
            style = VocalInkTheme.typography.bodyLarge,
            color = VocalInkTheme.colors.onPrimary
        )
    }
}