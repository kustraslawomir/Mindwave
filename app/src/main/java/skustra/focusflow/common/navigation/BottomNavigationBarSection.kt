package skustra.focusflow.common.navigation

sealed class BottomNavigationBarSection(val route: String) {
    object Session : BottomNavigationBarSection("session")
    object Statistics : BottomNavigationBarSection("statistics")
}