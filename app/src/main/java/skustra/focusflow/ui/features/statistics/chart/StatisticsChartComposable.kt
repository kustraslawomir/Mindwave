package skustra.focusflow.ui.features.statistics.chart

import android.graphics.Typeface
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.dp
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import skustra.focusflow.ui.utilities.math.convertToShortMinute
import com.patrykandpatrick.vico.compose.axis.axisLabelComponent
import com.patrykandpatrick.vico.core.entry.ChartEntryModelProducer

@Composable
fun StatisticsChartComposable(
    axisValuesOverrider: AxisValuesOverrider<ChartEntryModel>?,
    entryProducer: ChartEntryModelProducer
) {
    val thresholdLine = rememberThresholdLine()

    ProvideChartStyle(rememberChartStyle(listOf(MaterialTheme.colorScheme.primary))) {
        val lineChart = lineChart(
            targetVerticalAxisPosition = AxisPosition.Vertical.End,
            axisValuesOverrider = axisValuesOverrider,
            decorations = remember(thresholdLine) {
                listOf(thresholdLine)
            })
        Chart(
            chartModelProducer = entryProducer,
            chart = remember(lineChart) { lineChart },
            startAxis = startAxis(
                valueFormatter = verticalAxisValueFormatter,
                label = axisLabelComponent(color = MaterialTheme.colorScheme.onSurface),
                maxLabelCount = START_AXIS_LABEL_COUNT
            ),
            bottomAxis = bottomAxis(
                labelRotationDegrees = dateRotation,
                valueFormatter = horizontalAxisValueFormatter,
                label = axisLabelComponent(color = MaterialTheme.colorScheme.onSurface)
            ),
            marker = rememberMarker(),
            legend = rememberLegend(),
        )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdLine {

    val line = shapeComponent(
        strokeWidth = thresholdLineThickness,
        strokeColor = MaterialTheme.colorScheme.primary
    )

    val label = textComponent(
        background = shapeComponent(Shapes.pillShape, MaterialTheme.colorScheme.primary),
        padding = thresholdLineLabelPadding,
        margins = thresholdLineLabelMargins,
        typeface = Typeface.MONOSPACE,
    )

    return remember(line, label) {
        ThresholdLine(
            thresholdValue = THRESHOLD_LINE_VALUE,
            lineComponent = line,
            labelComponent = label
        )
    }
}

private const val THRESHOLD_LINE_VALUE = 90f
private const val dateRotation = 280f
private const val START_AXIS_LABEL_COUNT = 10
private val thresholdLineLabelMarginValue = 2.dp
private val thresholdLineThickness = 2.dp
private val thresholdLineLabelHorizontalPaddingValue = 6.dp
private val thresholdLineLabelVerticalPaddingValue = 2.dp
private val thresholdLineLabelPadding = dimensionsOf(
    thresholdLineLabelHorizontalPaddingValue,
    thresholdLineLabelVerticalPaddingValue
)

private val thresholdLineLabelMargins = dimensionsOf(thresholdLineLabelMarginValue)

val horizontalAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Horizontal.Bottom> { value, chartValues ->
        (chartValues.chartEntryModel.entries.firstOrNull()
            ?.getOrNull(value.toInt()) as? SessionArchiveEntry)
            ?.sessionArchiveEntryDataModel?.date
            ?.run { this }
            .orEmpty()
    }

val verticalAxisValueFormatter =
    AxisValueFormatter<AxisPosition.Vertical.Start> { value, chartValues ->
        (chartValues.chartEntryModel.entries.firstOrNull()
            ?.firstOrNull() as? SessionArchiveEntry)
            ?.sessionArchiveEntryDataModel?.summedDayDuration
            ?.run { value.convertToShortMinute() }
            .orEmpty()
    }
