package id.rllyhz.compose.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import id.rllyhz.compose.pullrefresh.PullRefresh
import id.rllyhz.compose.pullrefresh.PullRefreshMode
import id.rllyhz.compose.pullrefresh.indicator.AnimationType
import id.rllyhz.compose.pullrefresh.rememberPullRefreshState
import id.rllyhz.compose.sample.ui.theme.PullToRefreshTheme
import id.rllyhz.compose.sample.ui.widget.CardItem
import id.rllyhz.compose.sample.ui.widget.ExampleLottieAnimLoading
import kotlinx.coroutines.delay

class LottieAnimationSampleActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PullToRefreshTheme {
                // uncomment these two screens to see the different

                // MainScreen(AnimationType.Pulse)
                MainScreen(AnimationType.Wave)
            }
        }
    }
}

@Composable
private fun MainScreen(animationType: AnimationType) {
    var isRefreshing by remember { mutableStateOf(false) }

    // for lottie animation
    val composition by rememberLottieComposition(
        LottieCompositionSpec.RawRes(R.raw.lottie_loading_animation)
    )
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = LottieConstants.IterateForever,
        isPlaying = true,
        speed = 1f,
        restartOnPlay = false,
    )

    LaunchedEffect(isRefreshing) {
        if (isRefreshing) {
            delay(10000)
            isRefreshing = false
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center,
        ) {
            PullRefresh(
                state = rememberPullRefreshState(isRefreshing),
                loading = { state ->
                    ExampleLottieAnimLoading(
                        state,
                        composition = composition,
                        progress = progress,
                    )
                },
                mode = PullRefreshMode.Scrolling,
                loadingContainerMaxHeight = 90.dp,
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
}
