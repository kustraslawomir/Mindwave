package skustra.focusflow.main

import android.content.res.Resources
import androidx.compose.material.ScaffoldState
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.CoroutineScope

@Composable
fun rememberApplicationState(
    snackBarHostState: SnackbarHostState = remember { SnackbarHostState() },
    navController: NavController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember {
    ApplicationState(
        snackBarHostState,
        navController,
        resources,
        coroutineScope
    )
}

class ApplicationState(
    val snackBarHostState: SnackbarHostState,
    val navController: NavController,
    private val resources: Resources,
    val coroutineScope: CoroutineScope
)