package skustra.focusflow.ui.composables.session

import android.content.Context
import android.content.Intent
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.SessionCreator
import skustra.focusflow.domain.usecase.sessionstate.SessionStateHandler
import skustra.focusflow.ui.service.SessionForegroundService
import skustra.focusflow.ui.service.SessionForegroundService.Companion.DURATION_CHOSEN_BY_USER
import skustra.focusflow.ui.service.SessionForegroundService.Companion.SKIP_BREAKS
import javax.inject.Inject


@HiltViewModel
class SessionViewModel @Inject constructor(
    val drawableProvider: DrawableProvider,
    private val sessionHandler: SessionStateHandler
) : ViewModel() {

    private var skipTheBreaks = false

    private var durationChosenByUser = SessionConfig.DEFAULT_DURATION

    fun onSessionUiEvent(event: SessionUiEvent) {
        when (event) {
            is SessionUiEvent.Start -> startSession(event.context)
            SessionUiEvent.Pause -> sessionHandler.pauseSession()
            SessionUiEvent.Resume -> sessionHandler.resumeSession()
            SessionUiEvent.Stop -> sessionHandler.stopSession()
            SessionUiEvent.IncreaseDuration -> increaseSessionDuration()
            SessionUiEvent.DecreaseDuration -> decreaseSessionDuration()
            is SessionUiEvent.ShouldSkipTheBreaks -> skipTheBreaks(event.skip)
        }
    }

    fun skipTheBreaks() = skipTheBreaks

    fun getSessionFlow() = sessionHandler.sessionStateFlow

    fun isAvailableToIncrease(): Boolean {
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        return currentDurationIndex < SessionConfig.availableDurations().size - 1
    }

    fun isAvailableToDecrease(): Boolean {
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        return currentDurationIndex > 0
    }

    fun sessionIncludesBreaks(): Boolean {
        return durationChosenByUser > SessionConfig.minimalDurationToIncludeBreaks()
    }

    private fun startSession(context: Context) {
        ContextCompat.startForegroundService(
            context,
            Intent(context, SessionForegroundService::class.java).apply {
                putExtra(DURATION_CHOSEN_BY_USER, durationChosenByUser)
                putExtra(SKIP_BREAKS, skipTheBreaks)
            }
        )
    }

    private fun skipTheBreaks(skipBreaks: Boolean) {
        this.skipTheBreaks = skipBreaks

        viewModelScope.launch {
            sessionHandler.emitSession(SessionCreator.generate(durationChosenByUser, skipBreaks))
        }
    }

    private fun increaseSessionDuration() {
        if (!isAvailableToIncrease()) {
            return
        }

        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        durationChosenByUser = SessionConfig.availableDurations()[currentDurationIndex + 1]
        updateDuration(durationChosenByUser)
    }

    private fun decreaseSessionDuration() {
        if (!isAvailableToDecrease()) {
            return
        }

        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        durationChosenByUser = SessionConfig.availableDurations()[currentDurationIndex - 1]
        updateDuration(durationChosenByUser)
    }

    private fun updateDuration(durationChosenByUser: Minute) {
        this.durationChosenByUser = durationChosenByUser
        viewModelScope.launch {
            sessionHandler.emitSession(
                SessionCreator.generate(
                    durationChosenByUser,
                    skipTheBreaks
                )
            )
        }
    }
}