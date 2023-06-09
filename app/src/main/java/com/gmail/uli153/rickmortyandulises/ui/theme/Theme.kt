package com.gmail.uli153.rickmortyandulises.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

//private val DarkColorScheme = darkColorScheme(
//    primary = Green900,
//    onPrimary = Gray200,
//    secondary = Lime900,
//    onSecondary = Gray200,
//    tertiary = Orange900,
//    onTertiary = Gray200,
//    background = Gray900,
//    surface = Gray800,
//    onSurface = Gray200,
//    error = DeepOrange900,
//    onError = Gray200
//)


private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF97ce4c),
    onPrimary = White,
    secondary = Color(0xFFe89ac7),
    onSecondary = White,
    tertiary = Orange400,
    onTertiary = White,
    background = Gray100,
    surface = White,
    onSurface = Gray900,
    error = DeepOrange900,
    onError = Gray900
)

private val DarkColorScheme = LightColorScheme

@Composable
fun RMUTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.background.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = true
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}