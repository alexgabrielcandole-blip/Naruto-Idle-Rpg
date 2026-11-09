package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val NarutoDarkColorScheme = darkColorScheme(
    primary = NarutoOrange,
    onPrimary = LightText,
    primaryContainer = NarutoOrangeDark,
    onPrimaryContainer = LightText,
    secondary = ChakraCyan,
    onSecondary = NightNavyDark,
    secondaryContainer = NightNavySurface,
    onSecondaryContainer = LightText,
    tertiary = LeafGreen,
    onTertiary = LightText,
    background = NightNavyDark,
    onBackground = LightText,
    surface = NightNavy,
    onSurface = LightText,
    surfaceVariant = NightNavySurface,
    onSurfaceVariant = ShinobiSteel,
    outline = ShinobiSteel,
    error = CrimsonAlert,
    onError = Color.White
)

private val NarutoLightColorScheme = lightColorScheme(
    primary = NarutoOrange,
    onPrimary = LightText,
    primaryContainer = ParchmentDark,
    onPrimaryContainer = InkCharcoal,
    secondary = RoofJade,
    onSecondary = LightText,
    secondaryContainer = ParchmentLight,
    onSecondaryContainer = InkCharcoal,
    tertiary = LeafGreen,
    onTertiary = LightText,
    background = ParchmentBg,
    onBackground = InkCharcoal,
    surface = ParchmentLight,
    onSurface = InkCharcoal,
    surfaceVariant = ParchmentDark,
    onSurfaceVariant = InkSecondary,
    outline = InkSecondary,
    error = CrimsonAlert,
    onError = Color.White
)

@Composable
fun NarutoIdleTheme(
    darkTheme: Boolean = true, // Default to sleek ninja navy
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) NarutoDarkColorScheme else NarutoLightColorScheme
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
