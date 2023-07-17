package skustra.focusflow.common.navigation

import skustra.focusflow.R

sealed class BottomNavigationSection(val iconResourceId: Int, val route: String) {

    object SessionSection : BottomNavigationSection(
        iconResourceId = R.drawable.ic_home,
        route = BottomNavigationBarSection.Session.route
    )

    object StatisticsSection : BottomNavigationSection(
        iconResourceId = R.drawable.ic_statistics,
        route = BottomNavigationBarSection.Statistics.route
    )

    companion object {
        val sections = listOf(
            SessionSection,
            StatisticsSection
        )
    }
}