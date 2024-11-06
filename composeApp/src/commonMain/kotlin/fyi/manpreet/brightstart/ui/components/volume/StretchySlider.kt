package fyi.manpreet.brightstart.ui.components.volume

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.Easing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.awaitEachGesture
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.awaitHorizontalTouchSlopOrCancellation
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StretchySlider(
    modifier: Modifier = Modifier,
    defaultValue: Float,
    onValueChange: (Int) -> Unit,
) {

    // TODO Fix slider size
    // TODO Default value is 100. use cooerce in maybe

    var value by remember { mutableFloatStateOf(defaultValue / 100) }
    val animatedValue by animateFloatAsState(
        targetValue = value,
        spring(stiffness = Spring.StiffnessHigh),
        label = "animatedValue",
    )

    var scaleX by remember { mutableFloatStateOf(1f) }
    var scaleY by remember { mutableFloatStateOf(1f) }
    var translateX by remember { mutableFloatStateOf(0f) }
    var transformOrigin by remember { mutableStateOf(TransformOrigin.Center) }

    Slider(
        value = value,
        onValueChange = { value = it; onValueChange((it * 100).roundToInt()) },
        modifier = modifier
            .padding(horizontal = 32.dp)
            .graphicsLayer {
                this.transformOrigin = transformOrigin
                this.scaleX = scaleX
                this.scaleY = scaleY
                this.translationX = translateX
            },
        thumb = {},
        track = { sliderState ->
            val sliderFraction by remember {
                derivedStateOf {
                    (animatedValue - sliderState.valueRange.start) / (sliderState.valueRange.endInclusive - sliderState.valueRange.start)
                }
            }

            val density = LocalDensity.current
            val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr

            Box(
                modifier = Modifier
                    .trackOverslide(value = sliderFraction) { overslide ->
                        transformOrigin = TransformOrigin(
                            pivotFractionX = when (isLtr) {
                                true -> if (sliderFraction < .5f) 2f else -1f
                                false -> if (sliderFraction < .5f) -1f else 2f
                            },
                            pivotFractionY = .5f,
                        )

                        when (sliderFraction) {
                            in 0f..(.5f) -> {
                                scaleY = 1f + (overslide * .2f)
                                scaleX = 1f - (overslide * .2f)
                            }

                            else -> {
                                scaleY = 1f - (overslide * .2f)
                                scaleX = 1f + (overslide * .2f)
                            }
                        }

                        translateX = overslide * with(density) { 24.dp.toPx() }

                    }
                    .fillMaxWidth()
                    .height(64.dp)
                    .border(
                        width = 1.dp,
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = .5f), //MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                                Color.White.copy(alpha = .2f), //MaterialTheme.colorScheme.onSurface.copy(alpha = .2f),
                            )
                        ),
                        shape = RoundedCornerShape(16.dp)
                    )
                    .padding(4.dp)
                    .clip(shape = RoundedCornerShape(12.dp))
                    .background(
                        brush = Brush.horizontalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = .5f), //MaterialTheme.colorScheme.onSurface.copy(alpha = .5f),
                                Color.White.copy(alpha = .3f), //MaterialTheme.colorScheme.onSurface.copy(alpha = .3f),
                            )
                        )
                    )
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(sliderFraction)
                        .fillMaxHeight()
                        .background(
                            color = Color.White, //MaterialTheme.colorScheme.onSurface,
                        )
                )

                VolumeIcon(
                    volume = sliderFraction,
                    modifier = Modifier
                        .padding(start = 16.dp)
                        .align(Alignment.CenterStart)
                )
            }
        },
    )
}


@Composable
fun Modifier.trackOverslide(
    value: Float,
    onNewOverslideAmount: (Float) -> Unit,
): Modifier {

    val valueState = rememberUpdatedState(value)
    val scope = rememberCoroutineScope()
    val overslideAmountAnimatable = remember { Animatable(0f, .0001f) }
    var length by remember { mutableFloatStateOf(1f) }

    LaunchedEffect(Unit) {
        snapshotFlow { overslideAmountAnimatable.value }.collect {
            onNewOverslideAmount(CustomEasing.transform(it / length))
        }
    }

    val isLtr = LocalLayoutDirection.current == LayoutDirection.Ltr

    return onSizeChanged { length = it.width.toFloat() }
        .pointerInput(Unit) {
            awaitEachGesture {
                val down = awaitFirstDown()
                // User has touched the screen

                awaitHorizontalTouchSlopOrCancellation(down.id) { _, _ -> }
                // User has moved the minimum horizontal amount to recognize a drag

                var overslideAmount = 0f

                // Start tracking horizontal drag amount
                horizontalDrag(down.id) {
                    // Negate the change in X when Rtl language is used
                    val deltaX = it.positionChange().x * if (isLtr) 1f else -1f

                    // Clamp overslide amount
                    overslideAmount = when (valueState.value) {
                        0f -> (overslideAmount + deltaX).coerceAtMost(0f)
                        1f -> (overslideAmount + deltaX).coerceAtLeast(1f)
                        else -> 0f
                    }

                    // Animate to new overslide amount
                    scope.launch {
                        overslideAmountAnimatable.animateTo(overslideAmount)
                    }
                }
                // User has lifted finger off the screen
                // Drag has stopped

                // Animate overslide to 0, with a bounce
                scope.launch {
                    overslideAmountAnimatable.animateTo(
                        targetValue = 0f,
                        animationSpec = spring(
                            dampingRatio = .45f,
                            stiffness = Spring.StiffnessLow
                        )
                    )
                }
            }
        }
}


@Composable
fun VolumeIcon(
    volume: Float,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.graphicsLayer {
            transformOrigin = TransformOrigin(0f, .5f)
            val scale = 1f + (volume * .3f)
            scaleX = scale
            scaleY = scale
        },
    ) {
        val clipAnimationValue by animateFloatAsState(
            targetValue = if (volume != 0f) 1f else 0f,
            animationSpec = spring(stiffness = Spring.StiffnessLow),
            label = "clipAnimationValue"
        )
        remember { listOf(Origin.Top, Origin.Bottom) }.forEach { origin ->
            val ic = when (origin) {
                Origin.Top -> VolumeOffIcon//R.drawable.volume_off
                Origin.Bottom -> VolumeOnIcon//R.drawable.volume_on
            }
            Icon(
                imageVector = ic,
                modifier = Modifier
                    .clip(
                        value = when (origin) {
                            Origin.Top -> 1f - clipAnimationValue
                            Origin.Bottom -> clipAnimationValue
                        },
                        origin = origin,
                    ),
                tint = Color.Black, //MaterialTheme.colorScheme.surface,
                contentDescription = null,
            )
//            Icon(
//                painter = painterResource(
//                    when (origin) {
//                        Origin.Top -> R.drawable.volume_off
//                        Origin.Bottom -> R.drawable.volume_on
//                    }
//                ),
//                modifier = Modifier
//                    .clip(
//                        value = when (origin) {
//                            Origin.Top -> 1f - clipAnimationValue
//                            Origin.Bottom -> clipAnimationValue
//                        },
//                        origin = origin,
//                    ),
//                tint = MaterialTheme.colorScheme.surface,
//                contentDescription = null,
//            )
        }
    }

}

fun Modifier.clip(
    value: Float,
    origin: Origin
) = graphicsLayer {
    clip = true
    shape = when (origin) {
        Origin.Top -> object : Shape {
            override fun createOutline(
                size: Size, layoutDirection: LayoutDirection, density: Density
            ) = Outline.Rectangle(
                Rect(
                    offset = Offset.Zero,
                    size = Size(size.width, size.height * value)
                )
            )
        }

        Origin.Bottom -> object : Shape {
            override fun createOutline(
                size: Size, layoutDirection: LayoutDirection, density: Density
            ) = Outline.Rectangle(
                Rect(
                    offset = Offset(0f, size.height * (1f - value)),
                    size = Size(size.width, size.height * value)
                )
            )
        }
    }
}

sealed class Origin {
    data object Top : Origin()
    data object Bottom : Origin()
}


val CustomEasing: Easing = CubicBezierEasing(0.5f, 0.5f, 1.0f, 0.25f)