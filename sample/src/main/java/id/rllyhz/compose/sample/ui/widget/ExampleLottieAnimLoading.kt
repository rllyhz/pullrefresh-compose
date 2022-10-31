package id.rllyhz.compose.sample.ui.widget

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import id.rllyhz.compose.pullrefresh.PullRefreshState

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
        if (state.isPullInProgress) {
            Text("Pull more to refresh")
        } else if (state.isRefreshing) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
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
    }
}