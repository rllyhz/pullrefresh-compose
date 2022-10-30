package id.rllyhz.compose.pullrefresh.layout

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import id.rllyhz.compose.pullrefresh.PullRefreshState

/**
 * Container for scrolling the content.
 *
 * @param state The state of [PullRefreshState] the content should adapt to.
 */
@Composable
internal fun ContentContainer(
    state: PullRefreshState,
    refreshTriggerDistance: Dp,
    refreshingOffset: Dp,
    content: @Composable () -> Unit,
) {
    ScrollableContainer(
        state,
        refreshTriggerDistance,
        refreshingOffset,
    ) { updatedOffset ->
        Box(
            modifier = Modifier
                .graphicsLayer {
                    // Translate the scrollable content according to the offset
                    translationY = updatedOffset
                }
        ) {
            content()
        }
    }
}