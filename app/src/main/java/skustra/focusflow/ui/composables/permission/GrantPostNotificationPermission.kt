package skustra.focusflow.ui.composables.permission

import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.runtime.Composable
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import skustra.focusflow.ui.composables.postnotificationpermission.PostNotificationPermissionDialog
import skustra.focusflow.ui.composables.postnotificationpermission.PostNotificationPermissionRationaleDialog


@Composable
@OptIn(ExperimentalPermissionsApi::class)
fun GrantPostNotificationPermission(
    permissionState: PermissionState?,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    onClick: () -> Unit
) {
    if (permissionState == null) {
        return
    }

    val status = permissionState.status
    if (status !is PermissionStatus.Denied) {
        return
    }

    if (status.shouldShowRationale) {
        PostNotificationPermissionRationaleDialog(onClick = {
            onClick()
        })
        return
    }

    PostNotificationPermissionDialog(launcher = launcher, onClick = {
        onClick()
    })
}

