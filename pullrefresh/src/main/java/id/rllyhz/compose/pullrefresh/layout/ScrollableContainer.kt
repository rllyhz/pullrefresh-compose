package id.rllyhz.compose.pullrefresh.layout

import androidx.compose.animation.core.animate
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import id.rllyhz.compose.pullrefresh.PullRefreshState
import id.rllyhz.compose.pullrefresh.rememberUpdatedSlingshot

/**
 * Scrollable composable which is scrolling container of [PullRefresh]'s content block.
 *
 * @param state The state of [PullRefreshState] the content should adapt to.
 */
@Composable
fun ScrollableContainer(
    state: PullRefreshState,
    refreshTriggerDistance: Dp,
    refreshingOffset: Dp,
    content: @Composable () -> Unit,
) {
    val refreshingOffsetPx = with(LocalDensity.current) { refreshingOffset.toPx() }
    val refreshTrigger = with(LocalDensity.current) { refreshTriggerDistance.toPx() }

    val slingshot = rememberUpdatedSlingshot(
        offsetY = state.scrollableOffset,
        maxOffsetY = refreshTrigger,
        height = 0,
    )

    var offset by remember { mutableStateOf(0f) }

    if (state.isPullInProgress) {
        // If the user is currently pulling down, we use the 'slingshot' offset directly
        offset = slingshot.offset.toFloat()
    } else {
        // If there's no pulling down currently in progress, animate to the correct resting position
        LaunchedEffect(state.isRefreshing) {
            animate(
                initialValue = offset,
                targetValue = when {
                    state.isRefreshing -> refreshingOffsetPx
                    else -> 0f
                }
            ) { value, _ ->
                offset = value
            }
        }
    }

    content()
}
