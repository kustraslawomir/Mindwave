package skustra.focusflow.ui.features.drawer.model

import skustra.focusflow.R
import skustra.focusflow.common.navigation.DrawerNavigationSection

object DrawerItems {
    val items: List<AppDrawerItemInfo<DrawerNavigationSection>> = listOf(
        AppDrawerItemInfo(
            DrawerNavigationSection.Settings,
            R.string.settings,
            R.drawable.ic_home,
            R.string.settings
        )
    )
}