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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import skustra.focusflow.data.TimerState
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun SessionFocusArc(
    sessionState: TimerState,
    indicatorThickness: Dp = 7.dp,
    animationDuration: Int = SessionConfig.tickInterval().toInt()
) {

    val progress = when (sessionState) {
        is TimerState.InProgress -> sessionState.progress.percentageProgress()
        is TimerState.Paused -> sessionState.progress.percentageProgress()
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

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background

    val configuration = LocalConfiguration.current
    val composableSize = with(LocalContext.current.resources.displayMetrics) {
        configuration.screenWidthDp.dp.minus(80.dp)
    }
    Box(
        modifier = Modifier.size(composableSize), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(composableSize)
        ) {

            drawOutlineIndicator(animation, primaryColor, indicatorThickness, composableSize)
            drawShadow(Color.Black)
            drawShadowForeground(composableSize, indicatorThickness, backgroundColor)
        }

        ProgressText(
            sessionState = sessionState
        )
    }

    Spacer(modifier = Modifier.height(32.dp))
}

private fun DrawScope.drawOutlineIndicator(
    animation: State<Float>, foregroundIndicatorColor: Color, indicatorThickness: Dp, size: Dp
) {
    val sweepAngle = (animation.value) * 360 / 100
    drawArc(
        color = foregroundIndicatorColor,
        startAngle = -50f,
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

private fun DrawScope.drawShadowForeground(
    size: Dp,
    indicatorThickness: Dp,
    color: Color
) {
    drawCircle(
        color = color,
        radius = (size / 2 - indicatorThickness).toPx(),
        center = Offset(x = this.size.width / 2, y = this.size.height / 2)
    )
}

private fun DrawScope.drawShadow(shadowColor: Color) {
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(shadowColor, Color.Transparent),
            center = Offset(x = this.size.width / 2, y = this.size.height / 2),
            radius = this.size.height / 2
        ),
        radius = this.size.height / 2,
        center = Offset(x = this.size.width / 2, y = this.size.height / 2)
    )
}

@Composable
private fun ProgressText(
    sessionState: TimerState
) {

    val minutesLeft = when (sessionState) {
        is TimerState.InProgress -> sessionState.progress.minutesLeft.toString()
        is TimerState.Paused -> sessionState.progress.minutesLeft.toString()
        is TimerState.Idle -> SessionConfig.defaultSessionDuration().toString()
        else -> 0.toString()
    }

    Row(
    ) {
        Text(
            text = minutesLeft,
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            text = LocalizationManager.getText(LocalizationKey.MinutesShort),
            modifier = Modifier
                .align(Alignment.Bottom)
                .padding(vertical = 8.dp, horizontal = 8.dp),
            style = MaterialTheme.typography.labelMedium
        )
    }
}

