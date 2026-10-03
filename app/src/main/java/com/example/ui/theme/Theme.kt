package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val RedWhiteLightColorScheme = lightColorScheme(
    primary = AuraCrimson,
    onPrimary = AuraWhite,
    primaryContainer = AuraCrimsonSoft,
    onPrimaryContainer = AuraCrimsonDark,
    secondary = AuraCrimsonLight,
    onSecondary = AuraWhite,
    secondaryContainer = Color(0xFFFDECEC),
    onSecondaryContainer = AuraCrimsonDark,
    background = AuraWhiteOff,
    onBackground = AuraTextPrimary,
    surface = AuraWhite,
    onSurface = AuraTextPrimary,
    surfaceVariant = AuraWhiteWarm,
    onSurfaceVariant = AuraTextSecondary,
    outline = AuraSurfaceBorder,
    outlineVariant = AuraDivider,
    error = AllergenWarning,
    onError = AuraWhite
)

private val RedWhiteDarkColorScheme = darkColorScheme(
    primary = Color(0xFFEF5350),
    onPrimary = Color(0xFF1B0709),
    primaryContainer = Color(0xFF3E1218),
    onPrimaryContainer = Color(0xFFFFCDD2),
    secondary = Color(0xFFE57373),
    onSecondary = Color(0xFF1B0709),
    secondaryContainer = Color(0xFF2C1317),
    onSecondaryContainer = Color(0xFFFFCDD2),
    background = Color(0xFF140F10),
    onBackground = Color(0xFFFAF7F7),
    surface = Color(0xFF1D1618),
    onSurface = Color(0xFFFAF7F7),
    surfaceVariant = Color(0xFF281E21),
    onSurfaceVariant = Color(0xFFD6C8C9),
    outline = Color(0xFF453538),
    outlineVariant = Color(0xFF2F2426),
    error = Color(0xFFFF8A80),
    onError = Color(0xFF380B0D)
)

@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Keep bespoke Red & White brand aesthetic
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) RedWhiteDarkColorScheme else RedWhiteLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
