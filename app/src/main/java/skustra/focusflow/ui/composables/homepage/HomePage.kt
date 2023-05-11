package skustra.focusflow.ui.composables.homepage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import skustra.focusflow.ui.composables.session.SessionComposable
import skustra.focusflow.ui.composables.statistics.StatisticsComposable


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageComposable() {
    val pagerState = rememberPagerState()
    HorizontalPager(
        pageCount = homePages.size,
        state = pagerState
    ) { index ->
        when (homePages[index]) {
            Page.Session -> SessionComposable()
            Page.Statistics -> StatisticsComposable()
        }
    }
}
