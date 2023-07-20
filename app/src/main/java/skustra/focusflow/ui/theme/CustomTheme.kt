package skustra.focusflow.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val Primary = Color(0xFF4FBC7C)
private val Secondary = Color(0xFF82DDA8)
private val OnPrimary = Color(0xFF121212)

private val DarkBackground = Color(0xFF121212)
private val DarkSurface = Color(0xFF121212)
private val DarkOnSurface = Color(0xFFf2f2f2)

private val LightOnSurface = Color(0xFF121212)
private val LightSurface = Color(0xFF121212)
private val LightBackground = Color(0xFFf2f2f2)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    onPrimary = OnPrimary,
    background = DarkBackground,
    surface = DarkSurface,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    onPrimary = OnPrimary,
    background = LightBackground,
    surface = LightSurface,
    onSurface = LightOnSurface
)

@Composable
fun Theme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    isDynamicColor : Boolean = true,
    content: @Composable () -> Unit
) {
    val dynamicColor = isDynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S

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
        content = content,
        typography = Typography,
        shapes = Shapes
    )
}