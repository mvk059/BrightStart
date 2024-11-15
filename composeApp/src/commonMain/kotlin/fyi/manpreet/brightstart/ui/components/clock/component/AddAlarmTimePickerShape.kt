package fyi.manpreet.brightstart.ui.components.clock.component

import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import kotlin.apply

class AddAlarmTimePickerShape : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            fillType = PathFillType.EvenOdd

            // Define the rounded rectangle
            val cornerRadius = with(density) { 16.dp.toPx() }
            addRoundRect(
                RoundRect(
                    rect = Rect(0f, 0f, size.width, size.height),
                    topLeft = CornerRadius(0f, 0f),
                    topRight = CornerRadius(cornerRadius, cornerRadius),
                    bottomLeft = CornerRadius(cornerRadius, cornerRadius),
                    bottomRight = CornerRadius(cornerRadius, cornerRadius)
                )
            )

            println("size: $size")
            val bumpWidth05x = size.width * 0.05f
            val bumpWidth10x = size.width * 0.10f
            val bumpWidth35x = size.width * 0.35f
            val bumpWidth40x = size.width * 0.40f
            val bumpHeight = size.height * 0.15f
            val bumpHeight05x = size.height * 0.05f
            val bumpHeight10x = size.height * 0.10f
            val bumpCornerRadius = cornerRadius /// 2

//            moveTo(0f, 0f)
//            lineTo(0f, -bumpHeight10x)
//            lineTo(bumpWidth35x, -bumpHeight10x)
//            lineTo(bumpWidth40x, 0f)
//            close()



            // Move to the top left corner
            moveTo(0f, 0f)
            // Move up by 10% of the height
            lineTo(0f, -bumpHeight05x)
            // Add a rounded corner
            arcTo(
                rect = Rect(0f, -bumpHeight05x - bumpCornerRadius, bumpCornerRadius + bumpWidth05x, -bumpHeight05x + bumpCornerRadius),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )
            // Move left by 35% of the width
            lineTo(bumpWidth35x, -bumpHeight05x - bumpCornerRadius)
            // Move down by 10% of the height
            lineTo(bumpWidth40x, 0f)
            // Close the path
            close()

        }

        return Outline.Generic(path)
    }
}

class RoundedRectangleShape2() : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            fillType = PathFillType.EvenOdd

//            val cornerRadius = with(density) { cornerRadiusDp.dp.toPx() }
            val cornerRadius = with(density) { 16.dp.toPx() }

            // Start from the top-left corner
            moveTo(cornerRadius, 0f)

            // Top-right corner
            lineTo(size.width - cornerRadius, 0f)
            arcTo(
                rect = Rect(size.width - 2 * cornerRadius, 0f, size.width, 2 * cornerRadius),
                startAngleDegrees = 270f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Bottom-right corner
            lineTo(size.width, size.height - cornerRadius)
            arcTo(
                rect = Rect(size.width - 2 * cornerRadius, size.height - 2 * cornerRadius, size.width, size.height),
                startAngleDegrees = 0f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Bottom-left corner
            lineTo(cornerRadius, size.height)
            arcTo(
                rect = Rect(0f, size.height - 2 * cornerRadius, 2 * cornerRadius, size.height),
                startAngleDegrees = 90f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            // Top-left corner
            lineTo(0f, cornerRadius)
            arcTo(
                rect = Rect(0f, 0f, 2 * cornerRadius, 2 * cornerRadius),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 90f,
                forceMoveTo = false
            )

            close()
        }

        return Outline.Generic(path)
    }
}
