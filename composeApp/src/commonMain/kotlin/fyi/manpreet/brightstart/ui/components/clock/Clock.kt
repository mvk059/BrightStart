package fyi.manpreet.brightstart.ui.components.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.roundToInt
import kotlin.math.sin

@Composable
fun Clock(
    modifier: Modifier = Modifier,
) {
    val textMeasurer = rememberTextMeasurer()
    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var angle by remember { mutableFloatStateOf(0f) }
    var dragStartedAngle by remember { mutableFloatStateOf(0f) }
    var oldAngle by remember { mutableFloatStateOf(angle) }
    var selectedHour by remember { mutableIntStateOf(3) }
    val radius = 150.dp
    val scaleWidth = 100.dp
    val scaleIndicatorLength: Dp = 50.dp
    val minWeight = 0
    val maxWeight = 360
    val maxTextSize = 28.sp  // Maximum size for text when at indicator
    val minTextSize = 14.sp  // Minimum size for text when furthest from indicator

    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    // Calculate initial angle relative to circle center
                    dragStartedAngle = atan2(
                        offset.y - circleCenter.y,
                        offset.x - circleCenter.x
                    ) * (180f / PI.toFloat())
                },
                onDragEnd = {
                    oldAngle = angle
                }
            ) { change, _ ->
                // Calculate current touch angle
                val touchAngle = atan2(
                    change.position.y - circleCenter.y,
                    change.position.x - circleCenter.x
                ) * (180f / PI.toFloat())

                // Calculate the difference between current and start angle
                var deltaAngle = touchAngle - dragStartedAngle

                // Normalize the delta angle to handle wraparound
                if (deltaAngle > 180) {
                    deltaAngle -= 360
                } else if (deltaAngle < -180) {
                    deltaAngle += 360
                }

                // Update the total angle
                angle = (oldAngle + deltaAngle + 360) % 360

                // Calculate the selected hour based on the angle
                // Since the indicator is on the right (0 degrees) and we want 3 to be at the top (90 degrees),
                // we need to offset by 90 degrees and handle the wraparound
                val hourAngle = (360 - angle + 90) % 360
                selectedHour = ((hourAngle / 30).roundToInt() % 12).let {
                    if (it == 0) 12 else it
                }
            }
        }
    ) {

        circleCenter = Offset(
            center.x + (scaleWidth.toPx() / 2) - radius.toPx(),
            center.y,
        )

        val outerRadius = radius.toPx() + scaleWidth.toPx() / 2f
        val innerRadius = radius.toPx() - scaleWidth.toPx() / 2f

        // Draw main circle
        drawCircle(
            color = Color(0xFF36363d),
            radius = radius.toPx(),
            center = circleCenter,
            style = Stroke(
                width = scaleWidth.toPx()
            )
        )

        // Draw scale lines
        for (i in minWeight until maxWeight) {
            /*
        val angleInRadLine = (i + 90) * (PI / 180f).toFloat()

        // Calculate line length based on distance from current selected position
        val selectedAngle = ((selectedHour * 30) - 30) % 360 // Convert hour to angle
        val distanceFromSelected = abs((i - selectedAngle) % 360)
        val normalizedDistance = min(distanceFromSelected, 360 - distanceFromSelected) / 180f
        val lineLength = 5.dp.toPx() + (10.dp.toPx() * normalizedDistance).toFloat()

        val strokeWidth = 0.5.dp.toPx()

        val lineStart = Offset(
            x = outerRadius * cos(angleInRadLine) + circleCenter.x,
            y = outerRadius * sin(angleInRadLine) + circleCenter.y
        )

        val lineEnd = Offset(
            x = (outerRadius - lineLength) * cos(angleInRadLine) + circleCenter.x,
            y = (outerRadius - lineLength) * sin(angleInRadLine) + circleCenter.y
        )

        drawLine(
            color = Color.Red,
            start = lineStart,
            end = lineEnd,
            strokeWidth = strokeWidth
        )
            */

            val angleInRadLine = (i + 90) * (PI / 180f).toFloat()

            // Calculate line length based on angle
            val normalizedAngle = (i % 180) / 180f
            val lineLength = 5.dp.toPx() + (10.dp.toPx() * sin(normalizedAngle * PI)).toFloat()

            val strokeWidth = 0.5.dp.toPx()

            val lineStart = Offset(
                x = outerRadius * cos(angleInRadLine) + circleCenter.x,
                y = outerRadius * sin(angleInRadLine) + circleCenter.y
            )

            val lineEnd = Offset(
                x = (outerRadius - lineLength) * cos(angleInRadLine) + circleCenter.x,
                y = (outerRadius - lineLength) * sin(angleInRadLine) + circleCenter.y
            )

            drawLine(
                color = Color(0xFF5b5c63),
                start = lineStart,
                end = lineEnd,
                strokeWidth = strokeWidth
            )
        }

        // Draw hour numbers with dynamic sizing
        for (i in 1..12) {
            val hourAngle = (i * (360f / 12f) - 90 + angle) * (PI.toFloat() / 180f)

            // Calculate distance from selected hour position
            val hourDistance = abs(i - selectedHour)
            val normalizedDistance = min(hourDistance, 12 - hourDistance) / 6f

            // Calculate position-based scaling factor (right side larger than left)
            val positionAngle = (i * 30 - 270) % 360
            val distanceFromRight = abs(positionAngle % 360)
            val normalizedRightDistance = min(distanceFromRight, 360 - distanceFromRight) / 180f

            // Combine both factors with weights
            val selectionWeight = 0.7f // Weight for selection-based scaling
            val positionWeight = 0.3f // Weight for position-based scaling

            val combinedFactor = (normalizedDistance * selectionWeight +
                    normalizedRightDistance * positionWeight)

            // Calculate final text size - Note the direct use of combinedFactor here
            val dynamicTextSize = lerp(
                minTextSize.toPx(),
                maxTextSize.toPx(),
                1f - combinedFactor  // Invert the factor so selected hour is largest
            )

            val textRadius = outerRadius - 20.dp.toPx()
            val x = textRadius * cos(hourAngle) + circleCenter.x
            val y = textRadius * sin(hourAngle) + circleCenter.y

            val selectedHourFontSize = (dynamicTextSize + 20).toSp()
            val textResult = textMeasurer.measure(
                text = "$i",
                style = TextStyle(
                    fontSize = if (i == selectedHour) selectedHourFontSize else dynamicTextSize.toSp(),
                    fontWeight = if (i == selectedHour) FontWeight.Bold else FontWeight.Normal
                )
            )

            drawText(
                textLayoutResult = textResult,
                topLeft = Offset(
                    x - textResult.size.width / 2,
                    y - textResult.size.height / 2,
                ),
                color = if (i == selectedHour) Color.White else Color.LightGray,
            )
        }

        // Draw selected hour at the center
        val selectedHourText = textMeasurer.measure(
            text = "$selectedHour",
            style = TextStyle(
                fontSize = maxTextSize,
                fontWeight = FontWeight.Bold
            )
        )
        drawText(
            textLayoutResult = selectedHourText,
            topLeft = Offset(
                circleCenter.x - selectedHourText.size.width / 2,
                circleCenter.y - selectedHourText.size.height / 2
            ),
            color = Color.Cyan,
        )

        // Draw indicator on the right side
        val middleRight = Offset(
            x = circleCenter.x + innerRadius + scaleIndicatorLength.toPx(),
            y = circleCenter.y,
        )
        val bottomRight = Offset(
            x = circleCenter.x + innerRadius,
            y = circleCenter.y + 10f,
        )
        val topRight = Offset(
            x = circleCenter.x + innerRadius,
            y = circleCenter.y - 10f,
        )
        val indicator = Path().apply {
            moveTo(middleRight.x, middleRight.y)
            lineTo(bottomRight.x, bottomRight.y)
            lineTo(topRight.x, topRight.y)
            lineTo(middleRight.x, middleRight.y)
        }
        drawPath(
            path = indicator,
            color = Color(0xFF5b5c63)
        )
    }
}

// Helper function to linearly interpolate between two values
private fun lerp(start: Float, end: Float, fraction: Float): Float {
    return start + (end - start) * fraction
}
