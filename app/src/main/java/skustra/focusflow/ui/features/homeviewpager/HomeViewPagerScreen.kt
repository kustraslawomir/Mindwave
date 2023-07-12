package skustra.focusflow.ui.features.homeviewpager

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import skustra.focusflow.ui.features.session.SessionScreen
import skustra.focusflow.ui.features.session.pagericons.NavigateUpIcon
import skustra.focusflow.ui.features.session.pagericons.PagerIconsViewModel
import skustra.focusflow.ui.features.session.pagericons.StatisticsIcon
import skustra.focusflow.ui.features.statistics.StatisticsScreen

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeViewPagerScreen(viewModel: PagerIconsViewModel = viewModel()) {

    val pagerState: PagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) {
        pages.size
    }

    VerticalPager(
        state = pagerState,
    ) { index ->
        when (pages[index]) {
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