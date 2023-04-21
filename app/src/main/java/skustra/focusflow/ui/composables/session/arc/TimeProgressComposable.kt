package skustra.focusflow.ui.composables.session.arc

@Composable
fun TimeProgress(
    sessionState: Session
) {
    val minutesLeft = when (val timerState = sessionState.currentTimerState) {
        is TimerState.InProgress -> timerState.progress.minutesLeft.toString()
        is TimerState.Paused -> timerState.progress.minutesLeft.toString()
        is TimerState.Idle -> sessionState.sessionDuration().toString()
        else -> 0.toString()
    }

    Row {
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

