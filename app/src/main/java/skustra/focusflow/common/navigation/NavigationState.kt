package skustra.focusflow.common.navigation

import skustra.focusflow.main.ApplicationState

fun ApplicationState.popBackStack() {
    navController.popBackStack()
}

fun ApplicationState.navigate(route: String) {
    navController.navigate(route) {
        launchSingleTop = true
    }
}

fun ApplicationState.navigateAndPopBackStack(route: String, popUpRoute: String) {
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(popUpRoute) {
            inclusive = true
        }
    }
}

fun ApplicationState.navigateSaved(route: String, popUpRoute: String?) {
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
    navController.navigate(route) {
        launchSingleTop = true
        popUpTo(0) {
            inclusive = true
        }
    }
}