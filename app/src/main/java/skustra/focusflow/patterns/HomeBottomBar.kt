package skustra.focusflow.patterns


import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon

import androidx.compose.material.contentColorFor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import skustra.focusflow.common.navigation.NavigationSection
import skustra.focusflow.ui.theme.Accent


@Composable
fun HomeBottomBar(navController: NavHostController) {

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    BottomNavigation(
        backgroundColor = MaterialTheme.colorScheme.background,
        contentColor = contentColorFor(MaterialTheme.colorScheme.background),
        elevation = 10.dp
    ) {
        NavigationSection.sections.forEach { section ->
            val selected = currentDestination?.hierarchy?.any {
                it.route == section.route
            } == true
            BottomNavigationItem(icon = {
                Icon(
                    painter = painterResource(id = section.iconResourceId),
                    modifier = Modifier.size(24.dp),
                    contentDescription = ""
                )
            }, selected = selected,
                unselectedContentColor = Color.Gray,
                selectedContentColor = MaterialTheme.colorScheme.primary,
                onClick = {
                    navController.navigate(section.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                })
        }
    }
}