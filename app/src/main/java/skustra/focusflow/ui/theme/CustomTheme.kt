package skustra.focusflow.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val Primary = Color(0xFF4FBC7C)
private val Secondary = Color(0xFF82DDA8)
private val OnPrimary = Color(0xFF121212)

private val DarkBackground = Color(0xFF121212)
private val DarkOnSurface = Color(0xFFf2f2f2)
private val LightOnSurface = Color(0xFF121212)
private val LightBackground = Color(0xFFf2f2f2)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    secondary = Secondary,
    onPrimary = OnPrimary,
    background = DarkBackground,
    surface = DarkBackground,
    onSurface = DarkOnSurface
)

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    secondary = Secondary,
    onPrimary = OnPrimary,
    background = LightBackground,
    surface = LightBackground,
    onSurface = LightOnSurface
)

@Composable
fun Theme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {

    val colorScheme = when {
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