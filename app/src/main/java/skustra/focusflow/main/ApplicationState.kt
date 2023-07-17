package skustra.focusflow.main

import android.content.res.Resources
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
) = remember {
    ApplicationState(
        snackBarHostState,
        navController,
        resources,
        coroutineScope,
        drawerState
    )
}

class ApplicationState(
    val snackBarHostState: SnackbarHostState,
    val navController: NavHostController,
    private val resources: Resources,
    val coroutineScope: CoroutineScope,
    val drawerState: DrawerState
)