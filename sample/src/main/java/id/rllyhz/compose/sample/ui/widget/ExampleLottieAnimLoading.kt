package id.rllyhz.compose.sample.ui.widget

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import id.rllyhz.compose.pullrefresh.PullRefreshState
import kotlinx.coroutines.delay

@Composable
fun ExampleLottieAnimLoading(
    state: PullRefreshState,
    composition: LottieComposition?,
    progress: Float,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.BottomCenter,
    ) {
        if (state.isRefreshing) {
            var visible by remember { mutableStateOf(false) }
            LaunchedEffect(true) {
                delay(100)
                visible = true
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                AnimatedVisibility(
                    visible = visible,
                    enter = fadeIn(tween(1000)),
                    exit = fadeOut(),
                ) {
                    LottieAnimation(
                        composition,
                        progress,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(56.dp),
                        contentScale = ContentScale.FillWidth,
                    )
                }
            }
        } else if (state.isAbleToRelease && state.isPullInProgress) {
            Text("Release to refresh")
        } else if (state.isPullInProgress) {
            Text("Pull more to refresh")
        }
    }
}