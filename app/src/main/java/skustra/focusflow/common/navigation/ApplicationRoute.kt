package skustra.focusflow.common.navigation

sealed class ApplicationRoute(val route : String) {

    object Session : ApplicationRoute("session")
    object Statistics : ApplicationRoute("statistics")
}