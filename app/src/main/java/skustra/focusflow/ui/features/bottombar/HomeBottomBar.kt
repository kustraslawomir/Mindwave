package skustra.focusflow.ui.features.bottombar


import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import skustra.focusflow.common.navigation.BottomNavigationSectionsSource
import skustra.focusflow.common.navigation.navigateSaved
import skustra.focusflow.main.ApplicationState


@Composable
fun HomeBottomBar(
    applicationState: ApplicationState
) {
    val navBackStackEntry by applicationState.navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 10.dp
    ) {
        BottomNavigationSectionsSource.sections.forEach { section ->

            val selected = currentDestination?.hierarchy?.any {
                it.route == section.route
            } == true

            NavigationBarItem(icon = {
                Icon(
                    painter = painterResource(id = section.iconResourceId),
                    modifier = Modifier.size(24.dp),
                    contentDescription = ""
                )
            },
                selected = selected,

                /* unselectedContentColor = Color.Gray,
                 selectedContentColor = MaterialTheme.colorScheme.primary,*/
                onClick = {
                    applicationState.navigateSaved(
                        route = section.route,
                        popUpRoute = applicationState.navController.graph.findStartDestination().route
                    )
                })
        }
    }
}