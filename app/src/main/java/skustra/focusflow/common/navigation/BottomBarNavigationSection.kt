package skustra.focusflow.common.navigation

sealed class BottomBarNavigationSection(route: String) : Section(route) {
    object Session : BottomBarNavigationSection("session")
    object Statistics : BottomBarNavigationSection("statistics")
}