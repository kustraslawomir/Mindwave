package skustra.focusflow.ui.composables.statistics

import android.graphics.Typeface
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.compose.axis.horizontal.bottomAxis
import com.patrykandpatrick.vico.compose.axis.vertical.startAxis
import com.patrykandpatrick.vico.compose.chart.Chart
import com.patrykandpatrick.vico.compose.chart.column.columnChart
import com.patrykandpatrick.vico.compose.chart.line.lineChart
import com.patrykandpatrick.vico.compose.component.shapeComponent
import com.patrykandpatrick.vico.compose.component.textComponent
import com.patrykandpatrick.vico.compose.dimensions.dimensionsOf
import com.patrykandpatrick.vico.compose.style.ProvideChartStyle
import com.patrykandpatrick.vico.compose.style.currentChartStyle
import com.patrykandpatrick.vico.core.axis.AxisPosition
import com.patrykandpatrick.vico.core.axis.formatter.AxisValueFormatter
import com.patrykandpatrick.vico.core.chart.DefaultPointConnector
import com.patrykandpatrick.vico.core.chart.composed.plus
import com.patrykandpatrick.vico.core.chart.copy
import com.patrykandpatrick.vico.core.chart.decoration.ThresholdLine
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.component.shape.LineComponent
import com.patrykandpatrick.vico.core.component.shape.Shapes
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.SessionConfig.Companion.SESSION_MAX_DURATION_LIMIT
import skustra.focusflow.ui.extensions.toDisplayFormat
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ChartItemColor
import skustra.focusflow.ui.theme.GoalColor

@Composable
fun StatisticsComposable(viewModel: StatisticsViewModel = viewModel()) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Chart(viewModel)
            Text(
                LocalizationManager.getText(LocalizationKey.ChartDescription),
                modifier = Modifier
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                    .alpha(0.7f),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun Chart(viewModel: StatisticsViewModel) {
    val thresholdLine = rememberThresholdLine()
    ProvideChartStyle(rememberChartStyle(chartColors)) {
        val lineChart = lineChart(
            targetVerticalAxisPosition = AxisPosition.Vertical.End,
            axisValuesOverrider = getAxisValueOverrider(viewModel),
            decorations = remember(thresholdLine) {
                listOf(thresholdLine)
            })
        Chart(
            chartModelProducer = viewModel.getEntryProducer(),
            chart = remember(lineChart) { lineChart },
            startAxis = startAxis(
                valueFormatter = verticalAxisValueFormatter,
                maxLabelCount = START_AXIS_LABEL_COUNT,

                ),
            bottomAxis = bottomAxis(
                labelRotationDegrees = dateRotation,
                valueFormatter = horizontalAxisValueFormatter
            ),
            marker = rememberMarker(),
            legend = rememberLegend(),
        )
    }
}

@Composable
private fun rememberThresholdLine(): ThresholdLine {
    val line = shapeComponent(strokeWidth = thresholdLineThickness, strokeColor = GoalColor)
    val label = textComponent(
        color = Color.Black,
        background = shapeComponent(Shapes.pillShape, GoalColor),
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
private val chartColors = listOf(ChartItemColor, GoalColor)
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
            ?.run { value.toDisplayFormat() }
            .orEmpty()
    }

fun getAxisValueOverrider(viewModel: StatisticsViewModel): AxisValuesOverrider<ChartEntryModel> {
    return AxisValuesOverrider
        .fixed(
            minY = 0f,
            maxY = viewModel.getAxisValueMaxY()
        )
}
