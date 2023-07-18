package skustra.focusflow.ui.features.drawer.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class AppDrawerItemInfo<ApplicationRoute>(
    val applicationState: ApplicationRoute,
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val descriptionId: Int
)