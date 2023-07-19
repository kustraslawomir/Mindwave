package skustra.focusflow.ui.features.statistics.chart

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.core.component.shape.Shapes
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun rememberLegend() = verticalLegend(
    items = listOf(
        item(
            LocalizationManager.getText(LocalizationKey.DailyGoal),
            MaterialTheme.colorScheme.primary
        ),
        item(
            LocalizationManager.getText(LocalizationKey.YourProgress),
            MaterialTheme.colorScheme.primary
        )
    ),
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

@Composable
private fun item(labelText: String, labelColor: Color) = verticalLegendItem(
    icon = shapeComponent(Shapes.pillShape, labelColor),
    label = textComponent(
        color = labelColor,
        typeface = Typeface.MONOSPACE,
    ),
    labelText = labelText,
)

private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)