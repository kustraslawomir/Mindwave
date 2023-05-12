package skustra.focusflow.ui.composables.statistics
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
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.component.shape.Shapes

@Composable
fun rememberLegend() = verticalLegend(
    items = chartColors.mapIndexed { index, chartColor ->
        verticalLegendItem(
            icon = shapeComponent(Shapes.pillShape, chartColor),
            label = textComponent(
                color = currentChartStyle.axis.axisLabelColor,
                textSize = legendItemLabelTextSize,
                typeface = Typeface.MONOSPACE,
            ),
            labelText = "Test $index",
        )
    },
    iconSize = legendItemIconSize,
    iconPadding = legendItemIconPaddingValue,
    spacing = legendItemSpacing,
    padding = legendPadding,
)

private const val COLOR_1_CODE = 0xffb983ff
private const val COLOR_2_CODE = 0xff91b1fd
private const val COLOR_3_CODE = 0xff8fdaff
private const val COLOR_4_CODE = 0xfffab94d

private val color1 = Color(COLOR_1_CODE)
private val color2 = Color(COLOR_2_CODE)
private val color3 = Color(COLOR_3_CODE)
private val chartColors = listOf(color1, color2, color3)
private val legendItemLabelTextSize = 12.sp
private val legendItemIconSize = 8.dp
private val legendItemIconPaddingValue = 10.dp
private val legendItemSpacing = 4.dp
private val legendTopPaddingValue = 8.dp
private val legendPadding = dimensionsOf(top = legendTopPaddingValue)