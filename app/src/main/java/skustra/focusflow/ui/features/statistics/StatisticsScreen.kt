package skustra.focusflow.ui.features.statistics

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.patrykandpatrick.vico.core.chart.values.AxisValuesOverrider
import com.patrykandpatrick.vico.core.entry.ChartEntryModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import skustra.focusflow.domain.utilities.logs.AppLog
import skustra.focusflow.ui.features.statistics.chart.StatisticsChartComposable
import skustra.focusflow.ui.features.statistics.duration.StatisticsDurationComposable
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun StatisticsScreen(viewModel: StatisticsViewModel = hiltViewModel()) {

    val coroutineScope = rememberCoroutineScope()
    var data by remember { mutableStateOf(viewModel.getEmptyDurationUiModelList()) }
    var axisValuesOverrider by remember { mutableStateOf<AxisValuesOverrider<ChartEntryModel>?>(null) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            data = viewModel.getDurationStatistics()
            axisValuesOverrider = getAxisValueOverrider(viewModel.getAxisValueMaxY())
        }
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            StatisticsDurationComposable(data = data)
            Box(Modifier.height(24.dp))
            StatisticsChartComposable(
                axisValuesOverrider = axisValuesOverrider,
                entryProducer = viewModel.getEntryProducer()
            )
            Text(
                LocalizationManager.getText(LocalizationKey.ChartDescription),
                modifier = Modifier
                    .padding(top = 50.dp, start = 16.dp, end = 16.dp)
                    .alpha(0.5f),
                style = MaterialTheme.typography.bodySmall,
                color = Color.White,
                textAlign = TextAlign.Center
            )
        }
    }
}

fun getAxisValueOverrider(maxY: Float): AxisValuesOverrider<ChartEntryModel> {
    AppLog.log("MaxY: $maxY")
    return AxisValuesOverrider
        .fixed(
            minY = 0f,
            maxY = maxY
        )
}
