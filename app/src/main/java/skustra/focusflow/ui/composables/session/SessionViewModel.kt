package skustra.focusflow.ui.composables.session

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import skustra.focusflow.data.model.alias.Minute
import skustra.focusflow.data.model.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.model.session.Session
import skustra.focusflow.data.model.timer.TimerState
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.SessionCreator
import skustra.focusflow.domain.usecase.timer.Timer
import skustra.focusflow.domain.utilities.logs.AppLog
import skustra.focusflow.ui.extensions.vibratePhone
import timber.log.Timber
import javax.inject.Inject

const val TIMER_STATE = "timer_state"

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val timer: Timer,
    val resourceManager: DrawableProvider,
    private val application: Application,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    var currentSessionState = SessionCreator.generate()
    private val _sessionMutableStateFlow = MutableStateFlow(currentSessionState)
    val sessionStateFlow: StateFlow<Session> = _sessionMutableStateFlow

    private var durationChosenByUser = SessionConfig.DEFAULT_DURATION
    private var skipBreaks = false

    init {
        viewModelScope.launch {
            timer.getCurrentTimerState().stateIn(
                scope = viewModelScope,
                initialValue = currentSessionState,
                started = SharingStarted.WhileSubscribed(5000)
            ).collect { timerState ->
                if (timerState is TimerState) {
                    updateTimerState(timerState)
                }
            }
        }

        viewModelScope.launch {
            val savedTimerState: TimerState? = savedStateHandle[TIMER_STATE]
            Timber.w("Cached timer state: $savedTimerState")
            if (savedTimerState != null) {
                updateTimerState(savedTimerState)
            }
        }
    }

    private suspend fun updateTimerState(
        timerState: TimerState
    ) {
        if (timerState == TimerState.Completed) {
            try {
                application.applicationContext.vibratePhone()
                currentSessionState.activateTheNextPartOfTheSession()
                timer.start(
                    sessionDuration = currentSessionState
                        .currentSessionPart()
                        .sessionPartDuration,
                    scope = viewModelScope
                )
            } catch (exception: SessionAlreadyCompletedException) {
                exception.printStackTrace()
                timer.stop()
                return
            }
        }

        emiTimerState(timerState)
        AppLog.debugSession(currentSessionState.deepCopy())
    }

    fun skipBreaks(skipBreaks: Boolean) {
        this.skipBreaks = skipBreaks
        viewModelScope.launch {
            emitSession(SessionCreator.generate(durationChosenByUser, skipBreaks))
        }
    }

    fun startSession() {
        viewModelScope.launch {
            currentSessionState = SessionCreator.generate(durationChosenByUser, skipBreaks)
            timer.start(
                currentSessionState.currentSessionPart().sessionPartDuration,
                viewModelScope
            )
        }
    }

    fun pauseSession() {
        viewModelScope.launch {
            timer.pause()
        }
    }

    fun resumeSession() {
        viewModelScope.launch {
            timer.resume()
        }
    }

    fun stopSession() {
        viewModelScope.launch {
            timer.stop()
        }
    }

    fun increaseSessionDuration() {
        if (!isAvailableToIncrease()) {
            return
        }
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        durationChosenByUser = SessionConfig.availableDurations()[currentDurationIndex + 1]
        updateDuration(durationChosenByUser)
    }

    fun isAvailableToIncrease(): Boolean {
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        return currentDurationIndex < SessionConfig.availableDurations().size - 1
    }

    fun decreaseSessionDuration() {
        if (!isAvailableToDecrease()) {
            return
        }
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        durationChosenByUser = SessionConfig.availableDurations()[currentDurationIndex - 1]
        updateDuration(durationChosenByUser)

    }

    fun isAvailableToDecrease(): Boolean {
        val currentDurationIndex = SessionConfig.availableDurations().indexOf(durationChosenByUser)
        return currentDurationIndex > 0
    }

    private fun updateDuration(durationChosenByUser: Minute) {
        this.durationChosenByUser = durationChosenByUser
        viewModelScope.launch {
            emitSession(SessionCreator.generate(durationChosenByUser, skipBreaks))
        }
    }

    private suspend fun emiTimerState(state: TimerState) {
        Timber.d("Emit: $state ${state is TimerState.Completed}")
        //savedStateHandle[TIMER_STATE] = state
        _sessionMutableStateFlow.emit(currentSessionState.apply {
            currentTimerState = state
        }.deepCopy())
    }

    private suspend fun emitSession(session: Session) {
        _sessionMutableStateFlow.emit(session.deepCopy())
    }

    fun sessionIncludesBreaks(): Boolean {
        return durationChosenByUser > SessionConfig.minimalDurationToIncludeBreaks()
    }
}