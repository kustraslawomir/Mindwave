package skustra.focusflow.ui.features.statistics.duration

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.model.statistics.DurationUiModel

@Composable
fun StatisticsDurationComposable(data : List<DurationUiModel>) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = Modifier.padding(16.dp)
    ) {
        items(data.size) { index ->
            StatisticGridItem(durationUiModel = data[index])
        }
    }
}