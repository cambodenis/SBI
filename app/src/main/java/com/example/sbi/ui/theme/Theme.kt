package com.example.sbi.ui.theme


import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat

val BorderSize = 1.dp
val ButtonIndicator = 6.dp
private val DarkColorScheme = darkColorScheme(
    primary = White,
    background = BackgroundColor,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
)

private val LightColorScheme = lightColorScheme(
    primary = White,
    background = BackgroundColor,
    onPrimary = White,
    onSecondary = White,
    onTertiary = White,
)

@Composable
fun SBITheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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
        /* getting the current window by tapping into the Activity */
        val currentWindow = (view.context as? Activity)?.window
            ?: throw Exception("Not in an activity - unable to get Window reference")

        SideEffect {
            /* the default code did the same cast here - might as well use our new variable! */
            currentWindow.statusBarColor = colorScheme.primary.toArgb()
            /* accessing the insets controller to change appearance of the status bar, with 100% less deprecation warnings */
            WindowCompat.getInsetsController(currentWindow, view).isAppearanceLightStatusBars =
                darkTheme
        }
    }


    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
