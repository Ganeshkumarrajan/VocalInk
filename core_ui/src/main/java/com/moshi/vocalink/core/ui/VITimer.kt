package com.moshi.vocalink.core.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun VITimer(
    modifier: Modifier = Modifier,
    text: String
) {
    VTLabel(
        modifier = modifier,
        text = text
    )
}

