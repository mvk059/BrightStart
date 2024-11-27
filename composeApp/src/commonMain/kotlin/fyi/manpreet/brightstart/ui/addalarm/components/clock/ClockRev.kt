package fyi.manpreet.brightstart.ui.addalarm.components.clock

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectDragGestures
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
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@androidx.compose.runtime.Composable
fun ClockRev(
    modifier: Modifier = Modifier,
    minute: Int,
    onMinuteChange: (Int) -> Unit,
) {
    val textMeasurer = rememberTextMeasurer()
    var circleCenter by remember { mutableStateOf(Offset.Zero) }
    var angle by remember { mutableFloatStateOf(0f) }
    var dragStartedAngle by remember { mutableFloatStateOf(0f) }
    var oldAngle by remember { mutableFloatStateOf(angle) }
    var selectedMinute by remember { mutableIntStateOf(45) }
    val radius = 150.dp
    val scaleWidth = 100.dp
    val scaleIndicatorLength: Dp = 50.dp
    val minWeight = 0
    val maxWeight = 360
    val maxTextSize = 28.sp  // Maximum size for text when at indicator
    val minTextSize = (-5).sp   // Minimum size for text when furthest from indicator

    Canvas(
        modifier = modifier.pointerInput(Unit) {
            detectDragGestures(
                onDragStart = { offset ->
                    // Calculate initial angle relative to circle center
                    dragStartedAngle = atan2(
                        y = circleCenter.y - offset.y,
                        x = circleCenter.x - offset.x
                    ) * (180f / PI.toFloat())
                },
                onDragEnd = {
                    oldAngle = angle
                }
            ) { change, _ ->
                // Calculate current touch angle
                val touchAngle = atan2(
                    x = circleCenter.x - change.position.x,  // Difference between the x and y side
                    y = circleCenter.y - change.position.y,
                ) * (180f / PI.toFloat())

                // touchAngle - dragStartedAngle gives the angle between the touch position and the drag start position
                // + oldAngle gives the angle of the circle before the drag with respect to the circle center pointer
                val newAngle = oldAngle + (touchAngle - dragStartedAngle)
                angle = (newAngle + 360) % 360

                // Calculate the selected minute based on the angle
                // Since the indicator is on the left (180 degrees) and we want 9 to be at the top (270 degrees),
                // we need to offset by 270 degrees and handle the wraparound
                val minuteAngle = (360 - angle + 270) % 360
                selectedMinute = ((minuteAngle / 6).roundToInt() % 60).let {
                    if (it == 0) 0 else it
                }
                onMinuteChange(selectedMinute)
            }
        }
    ) {

        circleCenter = Offset(
            center.x + (scaleWidth.toPx()),// - radius.toPx(), //center.x + (scaleWidth.toPx()),// + radius.toPx(),
            center.y,// + radius.toPx() + 50.dp.toPx(),
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

        // Draw hour numbers
        for (i in 0..59) {
            if (i % 5 != 0) continue
            val hourAngle = (i * (360f / 60f) - 90 + angle) * (PI.toFloat() / 180f)

            val textRadius = outerRadius - 20.dp.toPx()
            val x = textRadius * cos(hourAngle) + circleCenter.x
            val y = textRadius * sin(hourAngle) + circleCenter.y

            val textResult = textMeasurer.measure(
                text = "$i",
                style = TextStyle(
                    fontSize = if (i == selectedMinute) 32.sp else 24.sp,
                    fontWeight = if (i == selectedMinute) FontWeight.Bold else FontWeight.Normal
                )
            )

            drawText(
                textLayoutResult = textResult,
                topLeft = Offset(
                    x - textResult.size.width / 2,
                    y - textResult.size.height / 2,
                ),
                color = if (i == selectedMinute) Color.White else Color.LightGray,
            )
        }

        // Draw selected minute at the center
        val selectedMinuteText = textMeasurer.measure(
            text = "$selectedMinute",
            style = TextStyle(
                fontSize = maxTextSize,
                fontWeight = FontWeight.Bold
            )
        )
//        drawText(
//            textLayoutResult = selectedMinuteText,
//            topLeft = Offset(
//                circleCenter.x - selectedMinuteText.size.width / 2,
//                circleCenter.y - selectedMinuteText.size.height / 2
//            ),
//            color = Color.Companion.Cyan,
//        )

        // Draw indicator on the left side
        val middleLeft = Offset(
            x = circleCenter.x - innerRadius - scaleIndicatorLength.toPx(),
            y = circleCenter.y,
        )
        val bottomLeft = Offset(
            x = circleCenter.x - innerRadius,
            y = circleCenter.y + 10f,
        )
        val topLeft = Offset(
            x = circleCenter.x - innerRadius,
            y = circleCenter.y - 10f,
        )
        val indicator = Path().apply {
            moveTo(middleLeft.x, middleLeft.y)
            lineTo(bottomLeft.x, bottomLeft.y)
            lineTo(topLeft.x, topLeft.y)
            lineTo(middleLeft.x, middleLeft.y)
        }
        drawPath(
            path = indicator,
            color = Color(0xFF5b5c63)
        )
    }
}
