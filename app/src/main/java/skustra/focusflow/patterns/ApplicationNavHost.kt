package skustra.focusflow.patterns

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import skustra.focusflow.common.navigation.BottomNavigationSectionsSource
import skustra.focusflow.common.navigation.DrawerNavigationSection
import skustra.focusflow.main.ApplicationState
import skustra.focusflow.ui.features.session.SessionScreen
import skustra.focusflow.ui.features.settings.SettingsScreen
import skustra.focusflow.ui.features.statistics.StatisticsScreen

@Composable
fun ApplicationNavHost(applicationState: ApplicationState, paddingValues: PaddingValues) {
    NavHost(
        navController = applicationState.navController,
        startDestination = BottomNavigationSectionsSource.SessionSection.route,
        modifier = Modifier.padding(paddingValues)
    ) {
        graph()
    }
}

fun NavGraphBuilder.graph() {

    val sessionRoute = BottomNavigationSectionsSource.SessionSection.route
    composable(sessionRoute) {
        SessionScreen()
    }

    val statisticsRoute = BottomNavigationSectionsSource.StatisticsSection.route
    composable(statisticsRoute) {
        StatisticsScreen()
    }

    val settingsRoute = DrawerNavigationSection.Settings.route
    composable(settingsRoute) {
        SettingsScreen()
    }
}