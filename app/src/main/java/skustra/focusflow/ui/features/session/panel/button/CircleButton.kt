package skustra.focusflow.ui.features.session.panel.button

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun CircleButton(onClick: () -> Unit, icon: Int, color: Color = MaterialTheme.colorScheme.primary) {
    IconButton(
        onClick = { onClick() },
        modifier = Modifier
            .size(46.dp)
            .background(color, CircleShape)
            .padding(6.dp),
        content = {
            Icon(
                painter = painterResource(id = icon),
                "$icon",
                tint = Color.Black
            )
        }
    )
}