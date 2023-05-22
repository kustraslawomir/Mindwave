package skustra.focusflow.ui.composables.session.panel

import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Context
import android.os.Build
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.ui.composables.postnotificationpermission.PostNotificationPermissionDialog
import skustra.focusflow.ui.composables.postnotificationpermission.PostNotificationPermissionRationaleDialog
import skustra.focusflow.ui.composables.session.SessionViewModel
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ButtonColor
import timber.log.Timber

@Composable
fun SessionPanelComposable(viewModel: SessionViewModel = viewModel()) {

    val sessionState by viewModel.getSessionStateFlow().collectAsStateWithLifecycle()
    when (sessionState.currentTimerState) {
        TimerState.Idle -> IdleSessionComposable(viewModel)
        is TimerState.InProgress -> PauseSessionComposable()
        is TimerState.Paused -> ResumeSessionGroupComposable()
        is TimerState.Completed -> {
            //ignore
        }
    }
}

@Composable
private fun IdleSessionComposable(viewModel: SessionViewModel) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (viewModel.sessionIncludesBreaks()) {
            SkipBreaksComposable(skipBreaks = viewModel.shouldSkipTheBreaks)
        }
        StartSessionComposable()
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun StartSessionComposable(viewModel: SessionViewModel = viewModel()) {
    val context = LocalContext.current
    val launcher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { wasGranted ->
        Timber.d("POST_NOTIFICATIONS permission was granted: $wasGranted")
        if (wasGranted) {
            viewModel.startSession(context)
        }
    }
    val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        rememberPermissionState(POST_NOTIFICATIONS)
    } else {
        null
    }

    val showPermissionDialog = remember { mutableStateOf(false) }
    val status = permissionState?.status
    Box(modifier = Modifier
        .clip(shape = RoundedCornerShape(size = 12.dp))
        .background(color = ButtonColor)
        .clickable {
            when (status) {
                is PermissionStatus.Granted -> viewModel.startSession(context)
                is PermissionStatus.Denied -> showPermissionDialog.value = true
                else -> viewModel.startSession(context)
            }

        }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = { }) {
                Icon(
                    painter = painterResource(id = viewModel.resourceManager.getPlayIcon()),
                    contentDescription = "",
                    tint = Color.Black
                )
            }
            Text(
                text = LocalizationManager.getText(LocalizationKey.CreateSession),
                modifier = Modifier.padding(end = 16.dp, bottom = 2.dp),
                color = Color.Black,
                style = MaterialTheme.typography.labelSmall,
            )
        }
        if (showPermissionDialog.value) {
            Dialog(
                onDismissRequest = {
                    showPermissionDialog.value = false
                    viewModel.startSession(context)
                }) {
                GrantPostNotificationPermission(permissionState = permissionState,
                    launcher = launcher,
                    onClick = {
                        showPermissionDialog.value = false
                    })
            }

        }
    }
}

@Composable
private fun PauseSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.pauseSession()
        }, icon = viewModel.resourceManager.getPauseIcon()
    )
}

@Composable
private fun ResumeSessionGroupComposable() {
    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
    ) {
        StopSessionComposable()
        Spacer(modifier = Modifier.width(16.dp))
        ResumeSessionComposable()
    }
}

@Composable
private fun ResumeSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.resumeSession()
        }, icon = viewModel.resourceManager.getResumeIcon()
    )
}

@Composable
private fun StopSessionComposable(viewModel: SessionViewModel = viewModel()) {
    CircleButton(
        onClick = {
            viewModel.stopSession()
        }, icon = viewModel.resourceManager.getStopIcon(), color = Color.White
    )
}

@Composable
@OptIn(ExperimentalPermissionsApi::class)
private fun GrantPostNotificationPermission(
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


