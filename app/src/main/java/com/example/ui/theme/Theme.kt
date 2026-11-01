package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val KidLightColorScheme = lightColorScheme(
    primary = KidSkyBlue,
    onPrimary = Color.White,
    primaryContainer = KidSkyBlueLight,
    onPrimaryContainer = KidSkyBlue,
    secondary = KidSunnyYellow,
    onSecondary = Color(0xFF3E2723),
    secondaryContainer = KidSunnyYellowLight,
    onSecondaryContainer = Color(0xFFE65100),
    tertiary = KidBubblePink,
    onTertiary = Color.White,
    tertiaryContainer = KidBubblePinkLight,
    onTertiaryContainer = KidBubblePink,
    background = KidBackground,
    onBackground = KidTextPrimary,
    surface = KidCardSurface,
    onSurface = KidTextPrimary,
    surfaceVariant = Color(0xFFE2E8F0),
    onSurfaceVariant = KidTextSecondary
)

private val KidDarkColorScheme = darkColorScheme(
    primary = KidSkyBlue,
    onPrimary = Color.White,
    secondary = KidSunnyYellow,
    onSecondary = Color.Black,
    tertiary = KidBubblePink,
    background = Color(0xFF0F172A),
    surface = Color(0xFF1E293B),
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun LittleGeniusTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // For kids educational apps, bright colorful theme provides optimal contrast and delight
    val colorScheme = if (darkTheme) KidDarkColorScheme else KidLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Backwards compatibility alias
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) = LittleGeniusTheme(darkTheme = darkTheme, content = content)
