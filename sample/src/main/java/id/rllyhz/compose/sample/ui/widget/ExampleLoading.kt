package id.rllyhz.compose.sample.ui.widget

import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import id.rllyhz.compose.pullrefresh.PullRefreshState

@Composable
fun ExampleLoading(
    state: PullRefreshState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier,
        contentAlignment = Alignment.BottomCenter
    ) {
        if (state.isRefreshing) {
            Text("Fetching new data...")
        } else if (state.isAbleToRelease && state.isPullInProgress) {
            Text("Release to refresh")
        } else if (state.isPullInProgress) {
            Text("Pull more to refresh")
        }
    }
}