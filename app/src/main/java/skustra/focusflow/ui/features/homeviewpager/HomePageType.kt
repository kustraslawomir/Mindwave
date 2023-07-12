package skustra.focusflow.ui.features.homeviewpager

sealed class PageType {
    object Session : PageType()
    object Statistics : PageType()
}

val pages = listOf(PageType.Session, PageType.Statistics)
