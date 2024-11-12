package fyi.manpreet.brightstart.ui.theme

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color

// TODO Fix colors
val DarkGrayPrimary = Color(0xFF4A4A4A)
val LightGrayButton = Color(0xFFB0B0B0)
val LightBackground = Color(0xFFF8F8F8)
val LightSurface = Color(0xFFFFFFFF)
val LightTextColor = Color(0xFF333333)

val WhitePrimary = Color(0xFFFFFFFF)
val DarkGrayButton = Color(0xFF4A4A4A)
val DarkBackground = Color(0xFF1C1C1C)
val DarkSurface = Color(0xFF2A2A2A)
val DarkTextColor = Color(0xFFE0E0E0)

internal val LightColorScheme = lightColorScheme(
    primary = DarkGrayPrimary,
    background = LightBackground,
    onBackground = LightTextColor,
    surface = LightSurface,
    onSurface = DarkGrayPrimary,
    secondary = LightGrayButton,
    onSecondary = LightTextColor
)

internal val DarkColorScheme = darkColorScheme(
    primary = WhitePrimary,
    background = DarkBackground,
    onBackground = DarkTextColor,
    surface = DarkSurface,
    onSurface = WhitePrimary,
    secondary = DarkGrayButton,
    onSecondary = DarkTextColor
)