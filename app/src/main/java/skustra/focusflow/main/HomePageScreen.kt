package skustra.focusflow.main

import android.content.res.Resources
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import skustra.focusflow.patterns.ApplicationNavHost
import skustra.focusflow.patterns.HomeBottomBar
import skustra.focusflow.ui.theme.Theme


@Composable
fun HomePageScreen() {
    Theme {
        Surface(
            color = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize()
        ) {
            val applicationState = rememberApplicationState()
            Scaffold(snackbarHost = {
                SnackbarHost(applicationState.snackBarHostState)
            }, content = { content ->
                println(content)
                ApplicationNavHost(
                    applicationState = applicationState,
                    paddingValues = PaddingValues(all = 16.dp)
                )
            },
                bottomBar = {
                    HomeBottomBar(navController = applicationState.navController)
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