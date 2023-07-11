package skustra.focusflow.ui.composables.permission

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import skustra.focusflow.domain.usecase.resources.DrawableProvider


@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun GrantPostNotificationPermission(
    permissionState: PermissionState?,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    onClick: () -> Unit,
    drawableProvider: DrawableProvider
) {
    if (permissionState == null) {
        return
    }

    val status = permissionState.status
    if (status !is PermissionStatus.Denied) {
        return
    }

    if (status.shouldShowRationale) {
        PostNotificationPermissionRationaleDialog(
            onRationaleClicked = onClick,
            drawableProvider = drawableProvider
        )
        return
    }

    PostNotificationPermissionDialog(
        launcher = launcher,
        onAllowClicked = onClick,
        drawableProvider = drawableProvider
    )
}

