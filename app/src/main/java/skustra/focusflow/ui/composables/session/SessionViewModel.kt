package skustra.focusflow.ui.composables.session

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import skustra.focusflow.data.alias.Minute
import skustra.focusflow.data.exceptions.SessionAlreadyCompletedException
import skustra.focusflow.data.session.Session
import skustra.focusflow.data.timer.TimerState
import skustra.focusflow.domain.logs.AppLog
import skustra.focusflow.domain.usecase.resources.DrawableProvider
import skustra.focusflow.domain.usecase.session.SessionConfig
import skustra.focusflow.domain.usecase.session.SessionCreator
import skustra.focusflow.domain.usecase.timer.Timer
import javax.inject.Inject

@HiltViewModel
class SessionViewModel @Inject constructor(
    private val timer: Timer,
    val resourceManager: DrawableProvider
) : ViewModel() {

    private val _sessionMutableStateFlow = MutableSharedFlow<Session>()
    val sessionStateFlow: SharedFlow<Session> = _sessionMutableStateFlow

    var currentSessionState = SessionCreator.generate()

    private var durationChosenByUser = SessionConfig.DEFAULT_DURATION
    private var skipBreaks = false

    init {
        viewModelScope.launch {
            timer.getCurrentTimerState().collect { timerState ->
                if (timerState == TimerState.Completed) {
                    try {
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
                        return@collect
                    }
                }

                emiTimerState(timerState)
                AppLog.debugSession(currentSessionState.deepCopy())
            }
        }
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
            timer.start(currentSessionState.currentSessionPart().sessionPartDuration, this)
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
        _sessionMutableStateFlow.emit(currentSessionState.apply {
            currentTimerState = state
        }.deepCopy())
    }

    private suspend fun emitSession(session: Session) {
        _sessionMutableStateFlow.emit(session.deepCopy())
    }
}