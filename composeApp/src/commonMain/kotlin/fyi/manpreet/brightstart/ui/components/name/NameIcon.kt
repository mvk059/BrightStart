package fyi.manpreet.brightstart.ui.components.name

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val NameIcon: ImageVector
    get() {
        if (Pencil != null) {
            return Pencil!!
        }
        Pencil = ImageVector.Builder(
            name = "Pencil",
            defaultWidth = 16.dp,
            defaultHeight = 16.dp,
            viewportWidth = 24f,
            viewportHeight = 24f
        ).apply {
            path(
                fill = null,
                fillAlpha = 1.0f,
                stroke = SolidColor(Color(0xFF0F172A)),
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.5f,
                strokeLineCap = StrokeCap.Round,
                strokeLineJoin = StrokeJoin.Round,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.NonZero
            ) {
                moveTo(16.8617f, 4.48667f)
                lineTo(18.5492f, 2.79917f)
                curveTo(19.2814f, 2.0669f, 20.4686f, 2.0669f, 21.2008f, 2.7992f)
                curveTo(21.9331f, 3.5314f, 21.9331f, 4.7186f, 21.2008f, 5.4508f)
                lineTo(6.83218f, 19.8195f)
                curveTo(6.3035f, 20.3481f, 5.6514f, 20.7368f, 4.9349f, 20.9502f)
                lineTo(2.25f, 21.75f)
                lineTo(3.04978f, 19.0651f)
                curveTo(3.2632f, 18.3486f, 3.6519f, 17.6965f, 4.1805f, 17.1678f)
                lineTo(16.8617f, 4.48667f)
                close()
                moveTo(16.8617f, 4.48667f)
                lineTo(19.5f, 7.12499f)
            }
        }.build()
        return Pencil!!
    }

private var Pencil: ImageVector? = null
