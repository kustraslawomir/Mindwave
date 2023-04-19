package skustra.focusflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DarkPrimary,
    secondary = DarkSecondary,
    background = DarkBackground
)

private fun isAdaptiveColorSchemeEnabled() = false

private fun defaultDarkTheme() = true

@Composable
fun isDarkThemeAllowed() = if (isAdaptiveColorSchemeEnabled()) isSystemInDarkTheme() else defaultDarkTheme()

private val LightColorScheme = lightColorScheme(
    primary = LightPrimary,
    secondary = LightSecondary,
    background = LightBackground
)

@Composable
fun AppTheme(
    isDarkTheme: Boolean,
    isDynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    //val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    val dynamicColor = false
    val colorScheme = when {
        dynamicColor && isDarkTheme -> {
            dynamicDarkColorScheme(LocalContext.current)
        }
        dynamicColor && !isDarkTheme -> {
            dynamicLightColorScheme(LocalContext.current)
        }
        isDarkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}