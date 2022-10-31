package id.rllyhz.compose.pullrefresh.indicator

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.*
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import id.rllyhz.compose.pullrefresh.PullRefreshState

enum class AnimationType {
    Pulse,
    Wave,
}

@Composable
fun SimpleIndicator(
    state: PullRefreshState,
    indicatorColors: List<Color> = listOf(Color.White, Color.White, Color.White),
    indicatorShapes: List<Shape> = listOf(CircleShape, CircleShape, CircleShape),
    elevation: Dp = 2.dp,
    size: Dp = 16.dp,
    gap: Dp = 10.dp,
    animationType: AnimationType = AnimationType.Pulse,
    durationInMillis: Int = 500,
    delayInMillis: Int = 300,
    easing: Easing? = null,
) {
    Box(
        modifier = Modifier
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        val keyframes = when (animationType) {
            AnimationType.Pulse -> listOf(0f, 0.42f, 0.60f)
            AnimationType.Wave -> listOf(0f, 0.5f, 1f)
        }

        val appliedEasing =
            easing ?: CubicBezierEasing(0.42f, 0.0f, 0.58f, 1.0f) // default is EaseInOut
        // or
        // CubicBezierEasing(0.950f, 0.050f, 0.795f, 0.035f) // easeInExpo
        // easing function could be customized

        AnimatedVisibility(
            visible = (state.isPullInProgress || state.isRefreshing),
            enter = fadeIn(
                tween(durationInMillis, delayInMillis)
            ),
            exit = fadeOut(
                tween(200)
            ),
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
            ) {
                // set the total of indicator for simplicity
                repeat(3) { index ->
                    val isMiddleIndicator = index == 1
                    val delayInKeyframe = (durationInMillis * keyframes[index]).toInt()
                    val color = indicatorColors.getOrNull(index) ?: Color.White
                    val shape = indicatorShapes.getOrNull(index) ?: CircleShape

                    if (isMiddleIndicator) Spacer(modifier = Modifier.width(gap))
                    ShapeIndicator(
                        animationType,
                        size,
                        durationInMillis,
                        delayInKeyframe,
                        appliedEasing,
                        color,
                        shape,
                        elevation
                    )
                    if (isMiddleIndicator) Spacer(modifier = Modifier.width(gap))
                }
            }
        }
    }
}

@Composable
private fun ShapeIndicator(
    animationType: AnimationType,
    size: Dp,
    durationInMillis: Int,
    keyframe: Int,
    easing: Easing,
    color: Color,
    shape: Shape,
    elevation: Dp,
) {
    val initialValue = when (animationType) {
        AnimationType.Pulse -> 0.6f
        AnimationType.Wave -> 8.4f
    }

    val targetValue = when (animationType) {
        AnimationType.Pulse -> 1.25f
        AnimationType.Wave -> -8.4f
    }

    val infiniteTransition = rememberInfiniteTransition()

    val adjustScale by infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = durationInMillis,
                easing = easing,
            ),
            repeatMode = RepeatMode.Reverse,
            initialStartOffset = StartOffset(keyframe, StartOffsetType.Delay)
        )
    )

    Surface(
        modifier = Modifier
            .size(size)
            .run {
                when (animationType) {
                    AnimationType.Pulse -> {
                        scale(adjustScale)
                    }
                    AnimationType.Wave -> {
                        graphicsLayer {
                            translationY = adjustScale
                        }
                    }
                }
            },
        shape = shape,
        color = color,
        elevation = elevation,
        content = {}
    )
}