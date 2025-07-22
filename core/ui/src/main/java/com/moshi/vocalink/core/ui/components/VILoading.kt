package com.moshi.vocalink.core.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.moshi.vocalink.core.ui.theme.VocalInkTheme
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens

@Composable
fun VILoading(
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            color = VocalInkThemeTokens.colors.secondary,
            modifier = Modifier.size(VocalInkThemeTokens.dimens.smallSize)
        )
    }
}
