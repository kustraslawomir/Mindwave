package skustra.focusflow.main

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import skustra.focusflow.R
import skustra.focusflow.common.navigation.DrawerSection

@Composable
fun <T : Enum<T>> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<DrawerSection>>,
    goToSettings: () -> Unit,
    goBack: () -> Unit,
    defaultPick: DrawerSection
) {

    val coroutineScope = rememberCoroutineScope()
    var currentPick by remember { mutableStateOf(defaultPick) }

    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_home),
                    contentDescription = "Main app icon"
                )
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // generates on demand the required composables
                    items(menuItems.size) { item ->
                        // custom UI representation of the button
                        AppDrawerItem(item = menuItems[item]) { navOption ->

                            // if it is the same - ignore the click
                            if (currentPick == navOption) {
                                return@AppDrawerItem
                            }

                            currentPick = navOption

                            // close the drawer after clicking the option
                            coroutineScope.launch {
                                drawerState.close()
                            }

                            when (navOption) {
                                DrawerSection.Settings -> goToSettings()
                            }
                        }
                    }
                }
            }
        }
    }
}


data class AppDrawerItemInfo<ApplicationRoute>(
    val applicationState: ApplicationRoute,
    @StringRes val title: Int,
    @DrawableRes val drawableId: Int,
    @StringRes val descriptionId: Int
)

object DrawerItems {
    val items: List<AppDrawerItemInfo<DrawerSection>> = listOf(
        AppDrawerItemInfo(
            DrawerSection.Settings, R.string.settings, R.drawable.ic_home, R.string.settings
        )
    )
}

@Composable
fun AppDrawerItem(
    item: AppDrawerItemInfo<DrawerSection>, onClick: (options: DrawerSection) -> Unit
) = Surface(
    color = MaterialTheme.colorScheme.onPrimary,
    modifier = Modifier.width(150.dp),
    onClick = { onClick(item.applicationState) },
    shape = RoundedCornerShape(50),
) {
    Row(
        horizontalArrangement = Arrangement.Start,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(16.dp)
    ) {
        Icon(
            painter = painterResource(id = item.drawableId),
            contentDescription = stringResource(id = item.descriptionId),
            modifier = Modifier.size(24.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = stringResource(id = item.title),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
        )
    }
}