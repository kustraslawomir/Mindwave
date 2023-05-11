package skustra.focusflow.ui.composables.homepage

sealed class Page {
    object Session : Page()
    object Statistics : Page()
}

val homePages = listOf(Page.Session, Page.Statistics)
