package skustra.focusflow.ui.composables.session.arc

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.sp
import skustra.focusflow.data.SessionState

@Composable
fun SessionFocusArc(
    size: Dp = 260.dp,
    foregroundIndicatorColor: Color = Color(0xFF35898f),
    shadowColor: Color = Color.LightGray,
    indicatorThickness: Dp = 20.dp,
    sessionState: SessionState,
    animationDuration: Int = 1000,
    dataTextStyle: TextStyle = MaterialTheme.typography.titleLarge,
    remainingTextStyle: TextStyle = MaterialTheme.typography.bodyMedium
) {

    val progress = when (sessionState) {
        is SessionState.SessionInProgress -> sessionState.sessionProgress.percentageProgress()
        is SessionState.SessionPaused -> sessionState.sessionProgress.percentageProgress()
        else -> 100f
    }

    val animation = animateFloatAsState(
        targetValue = progress, animationSpec = tween(
            durationMillis = animationDuration
        )
    )

    LaunchedEffect(Unit) {
        progress
    }

    Box(
        modifier = Modifier.size(size), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {

            drawShadow(shadowColor)
            drawOutlineIndicator(animation, foregroundIndicatorColor, indicatorThickness, size)
        }

        ProgressText(
            animateNumber = animation,
            titleTextStyle = dataTextStyle,
            subTitleTextStyle = remainingTextStyle
        )
    }

    Spacer(modifier = Modifier.height(32.dp))
    ButtonProgressbar {}
}

private fun DrawScope.drawOutlineIndicator(
    animation: State<Float>, foregroundIndicatorColor: Color, indicatorThickness: Dp, size: Dp
) {
    val sweepAngle = (animation.value) * 360 / 100
    drawArc(
        color = foregroundIndicatorColor,
        startAngle = -90f,
        sweepAngle = sweepAngle,
        useCenter = false,
        style = Stroke(width = indicatorThickness.toPx(), cap = StrokeCap.Round),
        size = Size(
            width = (size - indicatorThickness).toPx(), height = (size - indicatorThickness).toPx()
        ),
        topLeft = Offset(
            x = (indicatorThickness / 2).toPx(), y = (indicatorThickness / 2).toPx()
        )
    )
}

private fun DrawScope.drawShadow(shadowColor: Color) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(shadowColor, Color.White),
            center = Offset(x = this.size.width / 2, y = this.size.height / 2),
            radius = this.size.height / 2
        ),
        radius = this.size.height / 2,
        center = Offset(x = this.size.width / 2, y = this.size.height / 2)
    )
}

@Composable
private fun ProgressText(
    animateNumber: State<Float>, titleTextStyle: TextStyle, subTitleTextStyle: TextStyle
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = (animateNumber.value).toInt().toString() + " %", style = titleTextStyle
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            text = "Remaining", style = subTitleTextStyle
        )
    }
}

@Composable
private fun ButtonProgressbar(
    backgroundColor: Color = Color(0xFF35898f), onClickButton: () -> Unit
) {
    Button(
        onClick = {
            onClickButton()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    ) {
        Text(
            text = "TODO", color = Color.White, fontSize = 16.sp
        )
    }
}