package skustra.focusflow.common.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector

sealed class NavigationSection(val icon : ImageVector, val route : String) {
    companion object {
        val sections = listOf(
            SessionSection,
            StatisticsSection
        )
    }

    object SessionSection : NavigationSection(
        icon = Icons.Filled.Home,
        route = ApplicationRoute.Session.route
    )

    object StatisticsSection : NavigationSection(
        icon = Icons.Filled.Home,
        route = ApplicationRoute.Statistics.route
    )
}