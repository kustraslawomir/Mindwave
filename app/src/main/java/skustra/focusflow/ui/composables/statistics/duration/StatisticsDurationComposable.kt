package skustra.focusflow.ui.composables.statistics.duration

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skustra.focusflow.ui.composables.statistics.StatisticsViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun StatisticsDurationComposable(viewModel: StatisticsViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    var data by remember { mutableStateOf(viewModel.getEmptyDurationUiModelList()) }

    LaunchedEffect(key1 = Unit) {
        coroutineScope.launch(Dispatchers.IO) {
            data = viewModel.getDurationStatistics()
        }
    }

    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(16.dp)
    ) {
        items(data.size) { index ->
            StatisticGridItem(durationUiModel = data[index])
        }
    }
}