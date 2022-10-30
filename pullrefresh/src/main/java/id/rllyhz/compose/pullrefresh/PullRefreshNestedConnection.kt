package id.rllyhz.compose.pullrefresh

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.unit.Velocity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

/**
 * A simple implementation of [NestedScrollConnection]
 */
internal class PullRefreshNestedConnection(
    private val state: PullRefreshState,
    private val coroutineScope: CoroutineScope,
    private val onRefresh: () -> Unit,
) : NestedScrollConnection {
    var enabled: Boolean = false
    var refreshTrigger: Float = 0f

    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset = when {
        // If pull gesture isn't enabled, return zero
        !enabled -> Offset.Zero
        // If we're refreshing, return zero
        state.isRefreshing -> Offset.Zero
        // If the user is swiping up, handle it
        source == NestedScrollSource.Drag && available.y < 0 -> onScroll(available)
        else -> Offset.Zero
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset = when {
        // If swiping isn't enabled, return zero
        !enabled -> Offset.Zero
        // If we're refreshing, return zero
        state.isRefreshing -> Offset.Zero
        // If the user is pulling down and there's y remaining, handle it
        source == NestedScrollSource.Drag && available.y > 0 -> onScroll(available)
        else -> Offset.Zero
    }

    private fun onScroll(available: Offset): Offset {
        if (available.y > 0) {
            state.isPullInProgress = true
        } else if (state.scrollableOffset.roundToInt() == 0) {
            state.isPullInProgress = false
        }

        val newOffset = (available.y * DragMultiplier + state.scrollableOffset).coerceAtLeast(0f)
        val dragConsumed = newOffset - state.scrollableOffset

        return if (dragConsumed.absoluteValue >= 0.5f) {
            coroutineScope.launch {
                state.dispatchScrollDelta(dragConsumed)
            }
            // Return the consumed Y
            Offset(x = 0f, y = dragConsumed / DragMultiplier)
        } else {
            Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        // If we're dragging, not currently refreshing and scrolled
        // past the trigger point, refresh!
        if (!state.isRefreshing && state.scrollableOffset >= refreshTrigger) {
            onRefresh()
        }

        // Reset the drag in progress state
        state.isPullInProgress = false

        // Don't consume any velocity, to allow the scrolling layout to fling
        return Velocity.Zero
    }
}