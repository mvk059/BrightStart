package fyi.manpreet.brightstart.ui.components.volume

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val VolumeOffIcon: ImageVector
    get() {
        if (Volume_off != null) {
            return Volume_off!!
        }
        Volume_off = ImageVector.Builder(
            name = "Volume_off",
            defaultWidth = 24.dp,
            defaultHeight = 24.dp,
            viewportWidth = 960f,
            viewportHeight = 960f
        ).apply {
            path(
                fill = SolidColor(Color.Black),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(792f, 904f)
                lineTo(671f, 783f)
                quadToRelative(-25f, 16f, -53f, 27.5f)
                reflectiveQuadTo(560f, 829f)
                verticalLineToRelative(-82f)
                quadToRelative(14f, -5f, 27.5f, -10f)
                reflectiveQuadToRelative(25.5f, -12f)
                lineTo(480f, 592f)
                verticalLineToRelative(208f)
                lineTo(280f, 600f)
                horizontalLineTo(120f)
                verticalLineToRelative(-240f)
                horizontalLineToRelative(128f)
                lineTo(56f, 168f)
                lineToRelative(56f, -56f)
                lineToRelative(736f, 736f)
                close()
                moveToRelative(-8f, -232f)
                lineToRelative(-58f, -58f)
                quadToRelative(17f, -31f, 25.5f, -65f)
                reflectiveQuadToRelative(8.5f, -70f)
                quadToRelative(0f, -94f, -55f, -168f)
                reflectiveQuadTo(560f, 211f)
                verticalLineToRelative(-82f)
                quadToRelative(124f, 28f, 202f, 125.5f)
                reflectiveQuadTo(840f, 479f)
                quadToRelative(0f, 53f, -14.5f, 102f)
                reflectiveQuadTo(784f, 672f)
                moveTo(650f, 538f)
                lineToRelative(-90f, -90f)
                verticalLineToRelative(-130f)
                quadToRelative(47f, 22f, 73.5f, 66f)
                reflectiveQuadToRelative(26.5f, 96f)
                quadToRelative(0f, 15f, -2.5f, 29.5f)
                reflectiveQuadTo(650f, 538f)
                moveTo(480f, 368f)
                lineTo(376f, 264f)
                lineToRelative(104f, -104f)
                close()
                moveToRelative(-80f, 238f)
                verticalLineToRelative(-94f)
                lineToRelative(-72f, -72f)
                horizontalLineTo(200f)
                verticalLineToRelative(80f)
                horizontalLineToRelative(114f)
                close()
                moveToRelative(-36f, -130f)
            }
        }.build()
        return Volume_off!!
    }

private var Volume_off: ImageVector? = null
