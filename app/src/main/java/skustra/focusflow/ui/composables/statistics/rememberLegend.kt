package skustra.focusflow.ui.composables.statistics

import android.app.LocaleManager
import android.graphics.Typeface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.legend.verticalLegend
import com.patrykandpatrick.vico.compose.legend.verticalLegendItem
import com.patrykandpatrick.vico.compose.style.ChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.shape.Shapes
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ChartItemColor
import skustra.focusflow.ui.theme.GoalColor

@Composable
fun rememberLegend() = verticalLegend(
    items = listOf(
        item(LocalizationManager.getText(LocalizationKey.DailyGoal), GoalColor),
        item(LocalizationManager.getText(LocalizationKey.YourProgress), ChartItemColor)
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
        color = currentChartStyle.axis.axisLabelColor,
        textSize = legendItemLabelTextSize,
        typeface = Typeface.MONOSPACE,
    ),
    labelText = labelText,
)

private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)