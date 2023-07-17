package skustra.focusflow.common.navigation

import skustra.focusflow.R

sealed class BottomNavigationSectionsSource(val iconResourceId: Int, val route: String) {

    object SessionSection : BottomNavigationSectionsSource(
        iconResourceId = R.drawable.ic_home,
        route = BottomBarNavigationSection.Session.route
    )

    object StatisticsSection : BottomNavigationSectionsSource(
        iconResourceId = R.drawable.ic_statistics,
        route = BottomBarNavigationSection.Statistics.route
    )

    companion object {
        val sections = listOf(
            SessionSection,
            StatisticsSection
        )
    }
}