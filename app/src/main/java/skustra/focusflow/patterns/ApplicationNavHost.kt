package skustra.focusflow.patterns

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import skustra.focusflow.common.navigation.NavigationSection
import skustra.focusflow.main.ApplicationState
import skustra.focusflow.ui.features.session.SessionScreen
import skustra.focusflow.ui.features.statistics.StatisticsScreen

@Composable
fun ApplicationNavHost(applicationState: ApplicationState, paddingValues: PaddingValues) {
    NavHost(
        navController = applicationState.navController,
        startDestination = NavigationSection.SessionSection.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        appSoGraph(applicationState)
    }
}

fun NavGraphBuilder.appSoGraph(applicationState: ApplicationState) {
    composable(NavigationSection.SessionSection.route) {
        SessionScreen()
    }
    composable(NavigationSection.StatisticsSection.route) {
        StatisticsScreen()
    }
}