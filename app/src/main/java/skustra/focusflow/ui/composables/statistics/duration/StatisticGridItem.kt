package skustra.focusflow.ui.composables.statistics.duration

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.model.statistics.DurationUiModel

@Composable
fun StatisticGridItem(durationUiModel: DurationUiModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(1.3f)
            .padding(all = 5.dp)
            .clip(
                shape = RoundedCornerShape(24.dp)
            )
            .background(durationUiModel.color),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(
                text = durationUiModel.name,
                style = androidx.compose.material3.MaterialTheme.typography.titleSmall,
                color = Color.White
            )
            Text(
                text = durationUiModel.value,
                style = androidx.compose.material3.MaterialTheme.typography.titleLarge,
                color = Color.White
            )
        }
    }
}