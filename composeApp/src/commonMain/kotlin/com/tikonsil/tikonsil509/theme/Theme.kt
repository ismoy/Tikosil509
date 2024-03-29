package com.tikonsil.tikonsil509.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape

import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Surface
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import theme.md_theme_dark_background
import theme.md_theme_dark_error
import theme.md_theme_dark_errorContainer
import theme.md_theme_dark_inverseOnSurface
import theme.md_theme_dark_inversePrimary
import theme.md_theme_dark_inverseSurface
import theme.md_theme_dark_onBackground
import theme.md_theme_dark_onError
import theme.md_theme_dark_onErrorContainer
import theme.md_theme_dark_onPrimary
import theme.md_theme_dark_onPrimaryContainer
import theme.md_theme_dark_onSecondary
import theme.md_theme_dark_onSecondaryContainer
import theme.md_theme_dark_onSurface
import theme.md_theme_dark_onSurfaceVariant
import theme.md_theme_dark_onTertiary
import theme.md_theme_dark_onTertiaryContainer
import theme.md_theme_dark_outline
import theme.md_theme_dark_outlineVariant
import theme.md_theme_dark_primary
import theme.md_theme_dark_primaryContainer
import theme.md_theme_dark_scrim
import theme.md_theme_dark_secondary
import theme.md_theme_dark_secondaryContainer
import theme.md_theme_dark_surface
import theme.md_theme_dark_surfaceTint
import theme.md_theme_dark_surfaceVariant
import theme.md_theme_dark_tertiary
import theme.md_theme_dark_tertiaryContainer
import theme.md_theme_light_background
import theme.md_theme_light_error
import theme.md_theme_light_errorContainer
import theme.md_theme_light_inverseOnSurface
import theme.md_theme_light_inversePrimary
import theme.md_theme_light_inverseSurface
import theme.md_theme_light_onBackground
import theme.md_theme_light_onError
import theme.md_theme_light_onErrorContainer
import theme.md_theme_light_onPrimary
import theme.md_theme_light_onPrimaryContainer
import theme.md_theme_light_onSecondary
import theme.md_theme_light_onSecondaryContainer
import theme.md_theme_light_onSurface
import theme.md_theme_light_onSurfaceVariant
import theme.md_theme_light_onTertiary
import theme.md_theme_light_onTertiaryContainer
import theme.md_theme_light_outline
import theme.md_theme_light_outlineVariant
import theme.md_theme_light_primary
import theme.md_theme_light_primaryContainer
import theme.md_theme_light_scrim
import theme.md_theme_light_secondary
import theme.md_theme_light_secondaryContainer
import theme.md_theme_light_surface
import theme.md_theme_light_surfaceTint
import theme.md_theme_light_surfaceVariant
import theme.md_theme_light_tertiary
import theme.md_theme_light_tertiaryContainer

private val LightColorScheme = lightColorScheme(
    primary = md_theme_light_primary,
    onPrimary = md_theme_light_onPrimary,
    primaryContainer = md_theme_light_primaryContainer,
    onPrimaryContainer = md_theme_light_onPrimaryContainer,
    secondary = md_theme_light_secondary,
    onSecondary = md_theme_light_onSecondary,
    secondaryContainer = md_theme_light_secondaryContainer,
    onSecondaryContainer = md_theme_light_onSecondaryContainer,
    tertiary = md_theme_light_tertiary,
    onTertiary = md_theme_light_onTertiary,
    tertiaryContainer = md_theme_light_tertiaryContainer,
    onTertiaryContainer = md_theme_light_onTertiaryContainer,
    error = md_theme_light_error,
    errorContainer = md_theme_light_errorContainer,
    onError = md_theme_light_onError,
    onErrorContainer = md_theme_light_onErrorContainer,
    background = md_theme_light_background,
    onBackground = md_theme_light_onBackground,
    surface = md_theme_light_surface,
    onSurface = md_theme_light_onSurface,
    surfaceVariant = md_theme_light_surfaceVariant,
    onSurfaceVariant = md_theme_light_onSurfaceVariant,
    outline = md_theme_light_outline,
    inverseOnSurface = md_theme_light_inverseOnSurface,
    inverseSurface = md_theme_light_inverseSurface,
    inversePrimary = md_theme_light_inversePrimary,
    surfaceTint = md_theme_light_surfaceTint,
    outlineVariant = md_theme_light_outlineVariant,
    scrim = md_theme_light_scrim,
)

private val DarkColorScheme = darkColorScheme(
    primary = md_theme_dark_primary,
    onPrimary = md_theme_dark_onPrimary,
    primaryContainer = md_theme_dark_primaryContainer,
    onPrimaryContainer = md_theme_dark_onPrimaryContainer,
    secondary = md_theme_dark_secondary,
    onSecondary = md_theme_dark_onSecondary,
    secondaryContainer = md_theme_dark_secondaryContainer,
    onSecondaryContainer = md_theme_dark_onSecondaryContainer,
    tertiary = md_theme_dark_tertiary,
    onTertiary = md_theme_dark_onTertiary,
    tertiaryContainer = md_theme_dark_tertiaryContainer,
    onTertiaryContainer = md_theme_dark_onTertiaryContainer,
    error = md_theme_dark_error,
    errorContainer = md_theme_dark_errorContainer,
    onError = md_theme_dark_onError,
    onErrorContainer = md_theme_dark_onErrorContainer,
    background = md_theme_dark_background,
    onBackground = md_theme_dark_onBackground,
    surface = md_theme_dark_surface,
    onSurface = md_theme_dark_onSurface,
    surfaceVariant = md_theme_dark_surfaceVariant,
    onSurfaceVariant = md_theme_dark_onSurfaceVariant,
    outline = md_theme_dark_outline,
    inverseOnSurface = md_theme_dark_inverseOnSurface,
    inverseSurface = md_theme_dark_inverseSurface,
    inversePrimary = md_theme_dark_inversePrimary,
    surfaceTint = md_theme_dark_surfaceTint,
    outlineVariant = md_theme_dark_outlineVariant,
    scrim = md_theme_dark_scrim,
)

private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(2.dp),
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(16.dp),
    extraLarge = RoundedCornerShape(32.dp)
)

private val AppTypography = Typography(
    bodyMedium = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 16.sp
    )
)

internal val LocalThemeIsDark = compositionLocalOf { mutableStateOf(true) }

@Composable
internal fun AppTheme(
    content: @Composable() () -> Unit
) {
    val systemIsDark = isSystemInDarkTheme()
    val isDarkState = remember { mutableStateOf(systemIsDark) }
    CompositionLocalProvider(
        LocalThemeIsDark provides isDarkState
    ) {
        val isDark by isDarkState
        SystemAppearance(!isDark)
        MaterialTheme(
            colorScheme = if (isDark) DarkColorScheme else LightColorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = {
                Surface(content = content)
            }
        )
    }
}

@Composable
internal expect fun SystemAppearance(isDark: Boolean)
