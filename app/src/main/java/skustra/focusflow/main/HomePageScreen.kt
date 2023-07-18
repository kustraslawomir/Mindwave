package skustra.focusflow.main

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import skustra.focusflow.common.navigation.BottomBarNavigationSection
import skustra.focusflow.common.navigation.DrawerNavigationSection
import skustra.focusflow.common.navigation.navigate
import skustra.focusflow.common.navigation.popBackStack
import skustra.focusflow.patterns.ApplicationNavHost
import skustra.focusflow.ui.features.bottombar.HomeBottomBar
import skustra.focusflow.ui.features.drawer.AppDrawerContent
import skustra.focusflow.ui.features.drawer.model.DrawerItems
import skustra.focusflow.ui.theme.Theme


@Composable
fun HomePageScreen(viewModel: HomePageViewModel = hiltViewModel()) {
    Theme {
        Surface(
            color = MaterialTheme.colorScheme.background, modifier = Modifier.fillMaxSize()
        ) {
            val applicationState = rememberApplicationState()
            Scaffold(snackbarHost = {
                SnackbarHost(applicationState.snackBarHostState)
            }, content = { content ->
                println(content)
                ModalNavigationDrawer(
                    drawerState = applicationState.drawerState,
                    drawerContent = {

                        val settingsRoute = DrawerNavigationSection.Settings.route
                        val goToSettings: () -> Unit = {
                            applicationState.navigate(route = settingsRoute)
                        }
                        val goBack: () -> Unit = {
                            applicationState.popBackStack()
                        }

                        AppDrawerContent(
                            drawerState = applicationState.drawerState,
                            menuItems = DrawerItems.items,
                            goToSettings = goToSettings,
                            goBack = goBack
                        )
                    }
                ) {
                    ApplicationNavHost(
                        applicationState = applicationState,
                        paddingValues = PaddingValues(all = 16.dp)
                    )
                }
            }, bottomBar = {
                HomeBottomBar(
                    applicationState = applicationState
                )
            })
        }
    }
}

@Composable
@ReadOnlyComposable
fun resources(): Resources {
    LocalConfiguration.current
    return LocalContext.current.resources
}