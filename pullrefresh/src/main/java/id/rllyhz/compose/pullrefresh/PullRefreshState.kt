package id.rllyhz.compose.pullrefresh

import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.MutatePriority
import androidx.compose.foundation.MutatorMutex
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

/**
 * A state object that can be hoisted to control and observe changes for [PullRefresh].
 *
 * In most cases, this will be created via [rememberPullRefreshState].
 *
 * @param isRefreshing the initial value for [PullRefreshState.isRefreshing]
 */
class PullRefreshState(
    isRefreshing: Boolean
) {
    private val _scrollableOffset = Animatable(0f)
    private val mutatorMutex = MutatorMutex()

    /**
     * Whether this [PullRefreshState] is currently refreshing or not.
     */
    var isRefreshing: Boolean by mutableStateOf(isRefreshing)

    /**
     * Whether a pull/dragDown is currently in progress.
     */
    var isPullInProgress: Boolean by mutableStateOf(false)
        internal set

    /**
     * Whether a pull/dragDown is already able to release.
     */
    var isAbleToRelease: Boolean by mutableStateOf(false)
        internal set

    /**
     * The current offset for the scrollable, in pixels.
     */
    val scrollableOffset: Float get() = _scrollableOffset.value

    internal suspend fun animateOffsetTo(offset: Float) {
        mutatorMutex.mutate {
            _scrollableOffset.animateTo(offset)
        }
    }

    /**
     * Dispatch scroll delta in pixels from touch events.
     */
    internal suspend fun dispatchScrollDelta(delta: Float) {
        mutatorMutex.mutate(MutatePriority.UserInput) {
            _scrollableOffset.snapTo(_scrollableOffset.value + delta)
        }
    }
}