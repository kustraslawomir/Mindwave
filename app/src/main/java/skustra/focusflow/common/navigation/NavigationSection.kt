package skustra.focusflow.common.navigation

import skustra.focusflow.R

sealed class NavigationSection(val iconResourceId: Int, val route: String) {

    companion object {
        val sections = listOf(
            SessionSection,
            StatisticsSection
        )
    }

    object SessionSection : NavigationSection(
        iconResourceId = R.drawable.ic_home,
        route = ApplicationRoute.Session.route
    )

    object StatisticsSection : NavigationSection(
        iconResourceId = R.drawable.ic_statistics,
        route = ApplicationRoute.Statistics.route
    )
}