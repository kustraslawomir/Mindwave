package skustra.focusflow.common.navigation

sealed class DrawerSection(val route: String) {
    object Settings : DrawerSection("settings")
}

