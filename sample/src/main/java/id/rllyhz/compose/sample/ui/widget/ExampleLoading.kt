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
    if (state.isRefreshing) {
        Box(
            modifier,
            contentAlignment = Alignment.Center
        ) {
            Text("Fetching new data...")
        }
    } else if (state.isPullInProgress) {
        Box(
            modifier,
            contentAlignment = Alignment.Center
        ) {
            Text("Pull down to refresh")
        }
    }
}