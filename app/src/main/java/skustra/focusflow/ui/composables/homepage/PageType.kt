package skustra.focusflow.ui.composables.homepage

sealed class PageType {
    object Session : PageType()
    object Statistics : PageType()
}

val homePages = listOf(PageType.Session, PageType.Statistics)
