package skustra.focusflow.ui.features.permission

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun PostNotificationPermissionRationaleDialog(
    drawableProvider: DrawableProvider,
    onRationaleClicked: () -> Unit
) {
    val context = LocalContext.current
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black)
            .shadow(
                elevation = 1.dp,
                spotColor = Color.Gray
            ),
    ) {
        Column(
            modifier = Modifier
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = LocalizationManager.getText(LocalizationKey.PostPermissionNeedMessageRationale),
                textAlign = TextAlign.Center,
                //color color = Color.White,
            )
            Spacer(modifier = Modifier.height(32.dp))
            Box(modifier = Modifier
                .clip(shape = RoundedCornerShape(size = 12.dp))
               // .background(color = ButtonColor)
                .clickable {
                    context.startActivity(
                        Intent(
                            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                            Uri.fromParts("package", context.packageName, null)
                        )
                    )
                }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(onClick = onRationaleClicked) {
                        Icon(
                            painter = painterResource(id = drawableProvider.getNotificationIcon()),
                            contentDescription = "",
                            tint = Color.Black
                        )
                    }
                    Text(
                        text = LocalizationManager.getText(LocalizationKey.Allow),
                        modifier = Modifier.padding(end = 16.dp, bottom = 2.dp),
                        //color       color = Color.Black
                    )
                }
            }
        }
    }
}

