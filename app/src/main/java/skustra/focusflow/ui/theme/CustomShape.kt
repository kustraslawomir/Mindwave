package skustra.focusflow.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
import skustra.focusflow.ui.theme.CustomDimensions.DEFAULT_CORNERS_RADIUS

val Shapes = Shapes(
    small = RoundedCornerShape(DEFAULT_CORNERS_RADIUS.dp),
    medium = RoundedCornerShape(DEFAULT_CORNERS_RADIUS.dp),
    large = RoundedCornerShape(DEFAULT_CORNERS_RADIUS.dp)
)