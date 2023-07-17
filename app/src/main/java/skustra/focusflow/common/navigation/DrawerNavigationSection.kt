package skustra.focusflow.common.navigation

sealed class DrawerNavigationSection(route: String) : Section(route) {
    object Settings : DrawerNavigationSection("settings")
}

