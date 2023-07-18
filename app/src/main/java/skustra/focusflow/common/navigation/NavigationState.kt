package skustra.focusflow.common.navigation

import skustra.focusflow.main.ApplicationState
import skustra.focusflow.ui.utilities.logs.AppLog

fun ApplicationState.popBackStack() {
    AppLog.logNavigationPop()
    navController.popBackStack()
}

fun ApplicationState.navigate(route: String) {
    AppLog.navigate(route)
    navController.navigate(route) {
        launchSingleTop = true
    }
}

fun ApplicationState.navigateAndPopBackStack(route: String, popUpRoute: String) {
    AppLog.navigateAndPopBackStack(route, popUpRoute)
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUpRoute) {
            inclusive = true
        }
    }
}

fun ApplicationState.navigateSaved(route: String, popUpRoute: String?) {
    AppLog.navigateSaved(route, popUpRoute)
    navController.navigate(route) {
        launchSingleTop = true
        restoreState = true
        if (popUpRoute != null) {
            popUpTo(popUpRoute) {
                saveState = true
            }
        }

    }
}

fun ApplicationState.clearAndNavigate(route: String) {
    AppLog.clearAndNavigate(route)
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(0) {
            inclusive = true
        }
    }
}