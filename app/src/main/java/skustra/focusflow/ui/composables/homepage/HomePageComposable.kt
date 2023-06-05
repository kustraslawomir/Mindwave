package skustra.focusflow.ui.composables.homepage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import skustra.focusflow.ui.composables.session.SessionComposable
import skustra.focusflow.ui.composables.session.pagericons.ChevronUpComposable
import skustra.focusflow.ui.composables.session.pagericons.StatisticsIconComposable
import skustra.focusflow.ui.composables.statistics.StatisticsComposable


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageComposable() {
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        homePages.size
    }

    VerticalPager(
        state = pagerState
    ) { index ->
        when (homePages[index]) {
            PageType.Session -> Box {
                SessionComposable()
                StatisticsIconComposable(pagerState = pagerState)
            }

            PageType.Statistics -> Box {
                StatisticsComposable()
                ChevronUpComposable(pagerState = pagerState)
            }
        }
    }
}