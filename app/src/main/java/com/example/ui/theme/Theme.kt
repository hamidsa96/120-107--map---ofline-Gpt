package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

enum class AppThemeMode(val title: String, val icon: String) {
    LIGHT("حالت روز (روشن)", "☀️"),
    DARK("حالت شب (تاریک)", "🌙"),
    NIGHT_VISION("دید در شب (نظامی)", "🔴")
}

private val DarkColorScheme = darkColorScheme(
    primary = MilPale,
    onPrimary = MilDark,
    primaryContainer = MilOlive,
    onPrimaryContainer = MilPale,
    secondary = TacticalGold,
    onSecondary = Color.Black,
    secondaryContainer = TacticalAmberWarm,
    onSecondaryContainer = TacticalAmberLight,
    tertiary = TacticalBlue,
    onTertiary = Color.White,
    background = MilDarkBg,
    onBackground = MilDarkText,
    surface = MilDarkSurface,
    onSurface = MilDarkText,
    surfaceVariant = MilDarkCard,
    onSurfaceVariant = MilDarkTextSub,
    outline = MilDarkBorder,
    error = TargetRed,
    onError = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = MilOlive,
    onPrimary = Color.White,
    primaryContainer = MilPale,
    onPrimaryContainer = MilDark,
    secondary = TacticalAmber,
    onSecondary = Color.White,
    secondaryContainer = TacticalAmberLight,
    onSecondaryContainer = TacticalAmberWarm,
    tertiary = TacticalNavy,
    onTertiary = Color.White,
    background = MilLightBg,
    onBackground = MilLightText,
    surface = MilLightSurface,
    onSurface = MilLightText,
    surfaceVariant = MilLightCard,
    onSurfaceVariant = MilLightTextSub,
    outline = MilLightBorder,
    error = TargetRed,
    onError = Color.White
)

private val NightVisionColorScheme = darkColorScheme(
    primary = NightVisionRedBright,
    onPrimary = Color.Black,
    primaryContainer = NightVisionRedDark,
    onPrimaryContainer = NightVisionRedBright,
    secondary = NightVisionRed,
    onSecondary = Color.Black,
    secondaryContainer = NightVisionRedDark,
    onSecondaryContainer = NightVisionRedBright,
    tertiary = NightVisionRed,
    onTertiary = Color.Black,
    background = NightVisionBg,
    onBackground = NightVisionText,
    surface = NightVisionSurface,
    onSurface = NightVisionText,
    surfaceVariant = NightVisionCard,
    onSurfaceVariant = NightVisionTextSub,
    outline = NightVisionBorder,
    error = NightVisionRedBright,
    onError = Color.Black
)

@Composable
fun FireDirectionTheme(
    themeMode: AppThemeMode = AppThemeMode.LIGHT,
    content: @Composable () -> Unit
) {
    val colorScheme = when (themeMode) {
        AppThemeMode.LIGHT -> LightColorScheme
        AppThemeMode.DARK -> DarkColorScheme
        AppThemeMode.NIGHT_VISION -> NightVisionColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
