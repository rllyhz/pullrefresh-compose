package id.rllyhz.compose.pullrefresh

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

internal const val DragMultiplier = 0.5f

/**
 *
 * @param state the state object to be used to control or observe the [PullRefresh] state.
 * @param onRefresh Lambda which is invoked when a pull to refresh gesture is completed.
 * @param modifier the modifier to apply to this layout.
 * @param pullEnabled Whether the the layout should react to pull gestures or not.
 * @param loading The indicator composable to show when the state is either [PullRefreshState.isRefreshing] or [PullRefreshState.isPullInProgress].
 * @param loadingContainerMaxHeight The max height the loading composable could expand.
 * @param content The content containing a scroll composable.
 */
@Composable
fun PullRefresh(
    state: PullRefreshState,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
    mode: PullRefreshMode = PullRefreshMode.Pinned,
    pullEnabled: Boolean = true,
    loading: @Composable (state: PullRefreshState) -> Unit = { _ -> },
    loadingContainerMaxHeight: Dp = 56.dp,
    content: @Composable () -> Unit,
) {
    val coroutineScope = rememberCoroutineScope()
    val updatedOnRefresh = rememberUpdatedState(onRefresh)

    // LaunchedEffect, which animates the scrollable content to its resting position
    LaunchedEffect(state.isPullInProgress) {
        if (!state.isPullInProgress) {
            // If there's not a pull gestures in progress, rest the scrollable content at 0f
            state.animateOffsetTo(0f)
        }
    }

    val refreshTriggerPx = with(LocalDensity.current) { loadingContainerMaxHeight.toPx() }

    // get distance to trigger [PullRefreshState.isRefreshing] happens
    // it would be the same as the indicatorContainerMaxHeight
    var distance = refreshTriggerPx
    // we can add more pixels to the distance
    distance += 56

    val refreshTriggerDistance = distance.dp

    // nested scroll connection, which updates our state based on the scrolling event.
    val nestedScrollConnection = remember(state, coroutineScope) {
        PullRefreshNestedConnection(state, coroutineScope) {
            // On refresh, re-dispatch to the update onRefresh block
            updatedOnRefresh.value.invoke()
        }
    }.apply {
        this.enabled = pullEnabled
        this.refreshTrigger = refreshTriggerPx
    }

    Box(
        modifier = modifier
            .nestedScroll(connection = nestedScrollConnection)
    ) {
        // loading block goes here

        // content block goes here
    }
}