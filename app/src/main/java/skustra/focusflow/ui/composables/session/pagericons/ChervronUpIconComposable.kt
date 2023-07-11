package skustra.focusflow.ui.composables.session.pagericons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import skustra.focusflow.domain.usecase.resources.DrawableProvider

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun NavigateUpIcon(
    drawableProvider: DrawableProvider, pagerState: PagerState
) {
    val scope = rememberCoroutineScope()
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(end = 18.dp, bottom = 70.dp),
        contentAlignment = Alignment.BottomEnd,
    ) {
        IconButton(onClick = {
            scope.launch {
                pagerState.animateScrollToPage(0)
            }
        }) {
            Icon(
                painter = painterResource(id = drawableProvider.getChevronUpIcon()),
                modifier = Modifier.size(32.dp),
                tint = Color.White,
                contentDescription = ""
            )
        }
    }
}