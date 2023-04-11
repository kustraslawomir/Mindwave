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
import skustra.focusflow.data.SessionState
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.ui.localization.LocalizationKey
import skustra.focusflow.ui.localization.LocalizationManager

@Composable
fun SessionFocusArc(
    sessionState: SessionState,
    size: Dp = 280.dp,
    indicatorThickness: Dp = 15.dp,
    animationDuration: Int = SessionConfig.tickInterval().toInt(),
    titleStyle: TextStyle = MaterialTheme.typography.labelLarge
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

    val primaryColor = MaterialTheme.colorScheme.primary
    val backgroundColor = MaterialTheme.colorScheme.background
    Box(
        modifier = Modifier.size(size), contentAlignment = Alignment.Center
    ) {
        Canvas(
            modifier = Modifier.size(size)
        ) {

            drawOutlineIndicator(animation, primaryColor, indicatorThickness, size)
            drawShadowForeground(size, indicatorThickness, backgroundColor)
        }

        ProgressText(
            titleTextStyle = titleStyle,
            sessionState = sessionState
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

private fun DrawScope.drawShadowForeground(
    size: Dp,
    indicatorThickness: Dp,
    color : Color
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
            colors = listOf(shadowColor, Color.White),
            center = Offset(x = this.size.width / 2, y = this.size.height / 2),
            radius = this.size.height / 2
        ),
        radius = this.size.height / 2,
        center = Offset(x = this.size.width / 2, y = this.size.height / 2)
    )
}

@Composable
private fun ProgressText(titleTextStyle: TextStyle, sessionState : SessionState
) {

    val minutesLeft = when (sessionState) {
        is SessionState.SessionInProgress -> sessionState.sessionProgress.minutesLeft.toString()
        is SessionState.SessionPaused -> sessionState.sessionProgress.minutesLeft.toString()
        is SessionState.SessionIdle -> SessionConfig.defaultSessionDuration().toString()
        else -> 0.toString()
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "$minutesLeft min.", style = titleTextStyle
        )
    }
}

@Composable
private fun ButtonProgressbar(
    onClickButton: () -> Unit
) {
    val secondaryColor = MaterialTheme.colorScheme.secondary
    Button(
        onClick = {
            onClickButton()
        }, colors = ButtonDefaults.buttonColors(
            containerColor = secondaryColor
        )
    ) {
        Text(
            text = LocalizationManager.getText(LocalizationKey.Start)
        )
    }
}