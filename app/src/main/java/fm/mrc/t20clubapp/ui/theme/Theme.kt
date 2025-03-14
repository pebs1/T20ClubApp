package fm.mrc.t20clubapp.ui.theme

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
import androidx.core.view.WindowCompat

private val LightColorScheme = lightColorScheme(
    primary = DeepSkyBlue,
    onPrimary = CloudWhite,
    primaryContainer = SkyBlue,
    onPrimaryContainer = CloudWhite,
    secondary = SunsetOrange,
    onSecondary = CloudWhite,
    secondaryContainer = SunsetOrange,
    onSecondaryContainer = CloudWhite,
    tertiary = GrassGreen,
    onTertiary = CloudWhite,
    tertiaryContainer = GrassGreen,
    onTertiaryContainer = CloudWhite,
    background = CloudWhite,
    onBackground = NightBlue,
    surface = CloudWhite,
    onSurface = NightBlue
)

private val DarkColorScheme = darkColorScheme(
    primary = MoonlightBlue,
    onPrimary = StarWhite,
    primaryContainer = NightBlue,
    onPrimaryContainer = StarWhite,
    secondary = DuskOrange,
    onSecondary = StarWhite,
    secondaryContainer = DuskOrange,
    onSecondaryContainer = StarWhite,
    tertiary = DarkGrass,
    onTertiary = StarWhite,
    tertiaryContainer = DarkGrass,
    onTertiaryContainer = StarWhite,
    background = NightBlue,
    onBackground = StarWhite,
    surface = NightBlue,
    onSurface = StarWhite
)

@Composable
fun T20ClubAppTheme(
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
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}