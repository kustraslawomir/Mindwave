package skustra.focusflow.ui.composables.homepage

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.composables.session.SessionScreen
import skustra.focusflow.ui.composables.session.pagericons.NavigateUpIcon
import skustra.focusflow.ui.composables.session.pagericons.PagerIconsViewModel
import skustra.focusflow.ui.composables.session.pagericons.StatisticsIcon
import skustra.focusflow.ui.composables.statistics.StatisticsScreen


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomePageComposable() {
    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        homePages.size
    }

    val viewModel: PagerIconsViewModel = viewModel()
    VerticalPager(
        state = pagerState,
    ) { index ->
        when (homePages[index]) {
            PageType.Session -> Box {
                SessionScreen()
                StatisticsIcon(
                    pagerState = pagerState,
                    drawableProvider = viewModel.drawableProvider
                )
            }

            PageType.Statistics -> Box {
                StatisticsScreen()
                NavigateUpIcon(
                    pagerState = pagerState,
                    drawableProvider = viewModel.drawableProvider
                )
            }
        }
    }
}