package skustra.focusflow.ui.composables.session.panel.button

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.ui.composables.permission.GrantPostNotificationPermission
import skustra.focusflow.ui.composables.session.arc.BreaksCount
import skustra.focusflow.ui.composables.session.panel.SkipBreaksComposable
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager
import skustra.focusflow.ui.theme.ButtonColor
import timber.log.Timber

object PanelState {

    @Composable
    fun Idle(
        session: Session,
        sessionIncludesBreaks: Boolean,
        skipBreaks: Boolean,
        startSession: () -> Unit,
        drawableProvider: DrawableProvider
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            BreaksCount(session = session)

            if (sessionIncludesBreaks) {
                SkipBreaksComposable(skipBreaks = skipBreaks)
                Box(modifier = Modifier.height(height = 4.dp))
            } else {
                Box(modifier = Modifier.height(height = 12.dp))
            }
            Start(drawableProvider = drawableProvider, startSession = startSession)
        }
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    private fun Start(startSession: () -> Unit, drawableProvider: DrawableProvider) {
        val launcher = rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { permissionGranted ->
            Timber.d("POST_NOTIFICATIONS permission was granted: $permissionGranted")
            if (permissionGranted) {
                startSession()
            }
        }
        val permissionState = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            rememberPermissionState(Manifest.permission.POST_NOTIFICATIONS)
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
                    is PermissionStatus.Granted -> startSession()
                    is PermissionStatus.Denied -> showPermissionDialog.value = true
                    else -> startSession()
                }

            }) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = { }) {
                    Icon(
                        painter = painterResource(id = drawableProvider.getPlayIcon()),
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
                        startSession()
                    }) {
                    GrantPostNotificationPermission(permissionState = permissionState,
                        launcher = launcher,
                        onClick = {
                            showPermissionDialog.value = false
                        }, drawableProvider = drawableProvider
                    )
                }
            }
        }
    }

    @Composable
    fun Pause(drawableProvider: DrawableProvider, pauseSession: () -> Unit) {
        CircleButton(
            onClick = {
                pauseSession()
            }, icon = drawableProvider.getPauseIcon()
        )
    }

    @Composable
    fun Resume(
        drawableProvider: DrawableProvider,
        resumeSession: () -> Unit,
        stopSession: () -> Unit
    ) {
        Row(
            Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center
        ) {
            CircleButton(
                onClick = {
                    resumeSession()
                }, icon = drawableProvider.getResumeIcon()
            )
            Spacer(modifier = Modifier.width(16.dp))
            CircleButton(
                onClick = {
                    stopSession()
                }, icon = drawableProvider.getStopIcon(), color = Color.White
            )
        }
    }
}