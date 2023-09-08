package skustra.focusflow.ui.features.session.pagericons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import skustra.focusflow.domain.usecase.resources.DrawableProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StatisticsIcon(
    drawableProvider: DrawableProvider,
    pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 16.dp, top = 46.dp),
        contentAlignment = Alignment.TopEnd,
    ) {
        IconButton(
            onClick = {
                scope.launch {
                    pagerState.animateScrollToPage(1)
                }
            }
        ) {
            Icon(
                painter = painterResource(id = drawableProvider.getStatisticsIcon()),
                modifier = Modifier.size(24.dp),
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}