package skustra.focusflow.ui.composables.session.arc

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import skustra.focusflow.data.session.Session
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.SessionCreator

@Composable
fun SessionFocusArc(
    sessionState: Session,
    indicatorThickness: Dp = 7.dp
) {

    val progress = when (val currentTimerState = sessionState.currentTimerState) {
        is TimerState.InProgress -> currentTimerState.progress.percentageProgress()
        is TimerState.Paused -> currentTimerState.progress.percentageProgress()
        else -> 100f
    }

    val animation = animateFloatAsState(
        targetValue = progress, animationSpec = tween(
            durationMillis = SessionConfig.tickInterval().toInt()
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

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeProgress(sessionState = sessionState)
            CurrentSessionCounter(sessionState = sessionState)
            BreaksCount(sessionState = sessionState)
            ChangeSessionDurationComposable(sessionState = sessionState)
        }
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
    size: Dp, indicatorThickness: Dp, color: Color
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







