package skustra.focusflow.ui.features.drawer

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.DrawerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import skustra.focusflow.common.navigation.DrawerNavigationSection
import skustra.focusflow.ui.features.drawer.model.AppDrawerItemInfo

@Composable
fun <T : Enum<T>> AppDrawerContent(
    drawerState: DrawerState,
    menuItems: List<AppDrawerItemInfo<DrawerNavigationSection>>,
    goToSettings: () -> Unit,
    goBack: () -> Unit,
) {

    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Surface(color = MaterialTheme.colorScheme.background) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyColumn(
                    modifier = Modifier.padding(horizontal = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(menuItems.size) { item ->
                        AppDrawerItem(item = menuItems[item]) { navOption ->
                            coroutineScope.launch {
                                drawerState.close()
                            }
                            when (navOption) {
                                DrawerNavigationSection.Settings -> goToSettings()
                            }
                        }
                    }
                }
            }
        }
    }
}