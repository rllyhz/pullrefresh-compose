package id.rllyhz.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.rllyhz.compose.pullrefresh.PullRefresh
import id.rllyhz.compose.pullrefresh.PullRefreshMode
import id.rllyhz.compose.pullrefresh.rememberPullRefreshState
import id.rllyhz.compose.sample.ui.theme.PullToRefreshTheme
import id.rllyhz.compose.sample.ui.widget.CardItem
import id.rllyhz.compose.sample.ui.widget.ExampleLoading
import kotlinx.coroutines.delay

class PullRefreshModeSampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshTheme {
                // uncomment these two screens to see the different
                // MainScreen(PullRefreshMode.Pinned)
                MainScreen(PullRefreshMode.Scrolling)
            }
        }
    }
}

@Composable
fun MainScreen(pullRefreshMode: PullRefreshMode) {
    var isRefreshing by remember { mutableStateOf(false) }

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(3000)
            isRefreshing = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        PullRefresh(
            state = rememberPullRefreshState(isRefreshing),
            loading = { state ->
                ExampleLoading(state, Modifier.fillMaxSize())
            },
            mode = pullRefreshMode,
            loadingContainerMaxHeight = 42.dp,
            onRefresh = { isRefreshing = !isRefreshing },
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp, horizontal = 8.dp)
            ) {
                val itemCount = 40

                items(itemCount) {
                    CardItem(
                        text = "Card " + (it + 1).toString(),
                        modifier = Modifier.fillMaxWidth(),
                    )
                    if (it < (itemCount - 1)) Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
}