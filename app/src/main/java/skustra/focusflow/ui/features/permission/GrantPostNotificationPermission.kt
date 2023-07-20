package skustra.focusflow.ui.features.permission

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import skustra.focusflow.domain.usecase.resources.DrawableProvider


@Composable
@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterial3Api::class)
fun GrantPostNotificationPermission(
    permissionState: PermissionState?,
    launcher: ManagedActivityResultLauncher<String, Boolean>,
    drawableProvider: DrawableProvider,
    onDismissed: () -> Unit
) {
    if (permissionState == null) {
        return
    }

    val status = permissionState.status
    if (status !is PermissionStatus.Denied) {
        return
    }

    val context = LocalContext.current.applicationContext
    ModalBottomSheet(
        modifier = Modifier,
        sheetState = rememberModalBottomSheetState(
            skipPartiallyExpanded = false
        ),
        onDismissRequest = { onDismissed() },
        shape = RoundedCornerShape(
            topStart = 8.dp,
            topEnd = 8.dp
        ),
    ) {
        NotificationPermissionBottomSheet(
            onAllowed = {
                if (status.shouldShowRationale) {
                    openSettings(context = context)
                } else {
                    grantPermissions(launcher)
                }
                onDismissed()
            },
            drawableProvider = drawableProvider
        )
    }
}

fun openSettings(context: Context) {
    context.startActivity(
        Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", context.packageName, null)
        )
    )
}

fun grantPermissions(launcher: ManagedActivityResultLauncher<String, Boolean>) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        launcher.launch(Manifest.permission.POST_NOTIFICATIONS)
    }
}