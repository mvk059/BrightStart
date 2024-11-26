package fyi.manpreet.brightstart.ui.home.items.shape

import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

class TopBarMiniShape() : Shape {

    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {

        val path = Path().apply {
            fillType = PathFillType.EvenOdd

            val width = size.width
            val height = size.height
            val widthHalf = width / 2
            val width60 = width * 0.6f
            moveTo(widthHalf, 0f)

            lineTo(width60, height)
            lineTo(size.width, height)
            lineTo(size.width, 0f)
            close()
        }

        return Outline.Generic(path)
    }

}
