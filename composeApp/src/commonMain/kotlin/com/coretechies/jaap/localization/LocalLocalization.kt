package com.coretechies.jaap.localization

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.staticCompositionLocalOf

val LocalLocalization = staticCompositionLocalOf { Language.Hindi.isoFormat }

@Composable
fun LocalizedApp(language: String = Language.Hindi.isoFormat, content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalLocalization provides language,
        content = content
    )
}