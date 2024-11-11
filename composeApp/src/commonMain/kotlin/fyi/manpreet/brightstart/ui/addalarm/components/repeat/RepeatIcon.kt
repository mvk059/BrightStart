package fyi.manpreet.brightstart.ui.addalarm.components.repeat

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val RepeatIcon: ImageVector
    get() {
        if (Repeat != null) {
            return Repeat!!
        }
        Repeat = ImageVector.Builder(
            name = "Repeat",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 16f,
            viewportHeight = 16f
        ).apply {
            path(
                fill = SolidColor(Color(0xFF000000)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(11f, 5.466f)
                verticalLineTo(4f)
                horizontalLineTo(5f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -3.584f, 5.777f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = true, isPositiveArc = true, -0.896f, 0.446f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 5f, 3f)
                horizontalLineToRelative(6f)
                verticalLineTo(1.534f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.41f, -0.192f)
                lineToRelative(2.36f, 1.966f)
                curveToRelative(0.12f, 0.1f, 0.12f, 0.284f, 0f, 0.384f)
                lineToRelative(-2.36f, 1.966f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.41f, -0.192f)
                moveToRelative(3.81f, 0.086f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.67f, 0.225f)
                arcTo(5f, 5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11f, 13f)
                horizontalLineTo(5f)
                verticalLineToRelative(1.466f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.41f, 0.192f)
                lineToRelative(-2.36f, -1.966f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -0.384f)
                lineToRelative(2.36f, -1.966f)
                arcToRelative(0.25f, 0.25f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.41f, 0.192f)
                verticalLineTo(12f)
                horizontalLineToRelative(6f)
                arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.585f, -5.777f)
                arcToRelative(0.5f, 0.5f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0.225f, -0.67f)
                close()
            }
        }.build()
        return Repeat!!
    }

private var Repeat: ImageVector? = null
