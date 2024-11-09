package fyi.manpreet.brightstart.ui.components.empty

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.unit.dp

val ClockIcon: ImageVector
    get() {
        if (_Alarm != null) {
            return _Alarm!!
        }
        _Alarm = ImageVector.Builder(
            name = "Alarm",
            defaultWidth = 82.dp,
            defaultHeight = 82.dp,
            viewportWidth = 82f,
            viewportHeight = 82f
        ).apply {
            path(
                fill = SolidColor(Color(0xFFFFFFFF)),
                fillAlpha = 1.0f,
                stroke = null,
                strokeAlpha = 1.0f,
                strokeLineWidth = 1.0f,
                strokeLineCap = StrokeCap.Butt,
                strokeLineJoin = StrokeJoin.Miter,
                strokeLineMiter = 1.0f,
                pathFillType = PathFillType.EvenOdd
            ) {
                moveTo(25.6154f, 9.18051f)
                curveTo(26.91210f, 8.61350f, 27.50360f, 7.10270f, 26.93660f, 5.8060f)
                curveTo(26.36960f, 4.50930f, 24.85870f, 3.91780f, 23.56210f, 4.48480f)
                curveTo(15.85150f, 7.85660f, 9.3090f, 13.39340f, 4.69990f, 20.32560f)
                curveTo(3.91630f, 21.50410f, 4.23650f, 23.09470f, 5.4150f, 23.87830f)
                curveTo(6.59350f, 24.66190f, 8.18410f, 24.34170f, 8.96770f, 23.16320f)
                curveTo(13.03770f, 17.04170f, 18.81510f, 12.15420f, 25.61540f, 9.18050f)
                close()
                moveTo(58.4388f, 4.48485f)
                curveTo(57.14210f, 3.91780f, 55.63130f, 4.50930f, 55.06430f, 5.8060f)
                curveTo(54.49730f, 7.10270f, 55.08880f, 8.61350f, 56.38540f, 9.18050f)
                curveTo(63.18580f, 12.15420f, 68.96310f, 17.04170f, 73.03320f, 23.16320f)
                curveTo(73.81680f, 24.34170f, 75.40740f, 24.66190f, 76.58590f, 23.87830f)
                curveTo(77.76440f, 23.09470f, 78.08460f, 21.50410f, 77.3010f, 20.32560f)
                curveTo(72.69190f, 13.39340f, 66.14940f, 7.85660f, 58.43880f, 4.48480f)
                close()
                moveTo(66.4517f, 64.5619f)
                curveTo(70.83880f, 59.02690f, 73.45850f, 52.02750f, 73.45850f, 44.41620f)
                curveTo(73.45850f, 26.48990f, 58.92650f, 11.95780f, 41.00020f, 11.95780f)
                curveTo(23.0740f, 11.95780f, 8.54190f, 26.48990f, 8.54190f, 44.41620f)
                curveTo(8.54190f, 52.02730f, 11.16150f, 59.02660f, 15.54840f, 64.56150f)
                lineTo(8.25952f, 73.5526f)
                curveTo(7.36830f, 74.65190f, 7.5370f, 76.26560f, 8.63640f, 77.15680f)
                curveTo(9.73570f, 78.04810f, 11.34940f, 77.87930f, 12.24060f, 76.780f)
                lineTo(19.0747f, 68.35f)
                curveTo(24.85030f, 73.64380f, 32.5480f, 76.87450f, 41.00020f, 76.87450f)
                curveTo(49.45230f, 76.87450f, 57.14980f, 73.6440f, 62.92540f, 68.35030f)
                lineTo(69.7596f, 76.78f)
                curveTo(70.65080f, 77.87940f, 72.26450f, 78.0480f, 73.36380f, 77.15680f)
                curveTo(74.46320f, 76.26550f, 74.63190f, 74.65180f, 73.74060f, 73.55250f)
                lineTo(66.4517f, 64.5619f)
                close()
                moveTo(41f, 24.7703f)
                curveTo(42.41520f, 24.77030f, 43.56250f, 25.91760f, 43.56250f, 27.33280f)
                verticalLineTo(43.0448f)
                lineTo(52.6714f, 49.1174f)
                curveTo(53.8490f, 49.90240f, 54.16720f, 51.49340f, 53.38210f, 52.67090f)
                curveTo(52.59710f, 53.84850f, 51.00610f, 54.16670f, 49.82860f, 53.38160f)
                lineTo(39.5786f, 46.5483f)
                curveTo(38.86570f, 46.07310f, 38.43750f, 45.2730f, 38.43750f, 44.41620f)
                verticalLineTo(27.3328f)
                curveTo(38.43750f, 25.91760f, 39.58480f, 24.77030f, 410f, 24.77030f)
                close()
            }
        }.build()
        return _Alarm!!
    }

private var _Alarm: ImageVector? = null
