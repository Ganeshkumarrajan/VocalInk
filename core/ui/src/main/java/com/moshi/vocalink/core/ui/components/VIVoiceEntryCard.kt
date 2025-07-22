package com.moshi.vocalink.core.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.moshi.vocalink.core.ui.theme.VocalInkThemeTokens

@Composable
fun VoiceEntryCard(
    timestamp: String,
    content: String,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = VocalInkThemeTokens.dimens.ScreenPadding),
        shape = RoundedCornerShape(VocalInkThemeTokens.dimens.CardCornerRadius),
        elevation = CardDefaults.cardElevation(VocalInkThemeTokens.dimens.ScreenPadding),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier.padding(VocalInkThemeTokens.dimens.ScreenPadding),
            verticalArrangement = Arrangement.spacedBy(VocalInkThemeTokens.dimens.Small)
        ) {
            Text(
                text = timestamp,
                style = MaterialTheme.typography.labelLarge.copy(
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold
                )
            )

            Text(
                text = content,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                )
            )
        }
    }
}
