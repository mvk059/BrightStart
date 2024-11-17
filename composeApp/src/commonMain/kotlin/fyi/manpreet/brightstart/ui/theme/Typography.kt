package fyi.manpreet.brightstart.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import brightstart.composeapp.generated.resources.Poppins_Black
import brightstart.composeapp.generated.resources.Poppins_BlackItalic
import brightstart.composeapp.generated.resources.Poppins_Bold
import brightstart.composeapp.generated.resources.Poppins_BoldItalic
import brightstart.composeapp.generated.resources.Poppins_ExtraBold
import brightstart.composeapp.generated.resources.Poppins_ExtraBoldItalic
import brightstart.composeapp.generated.resources.Poppins_ExtraLight
import brightstart.composeapp.generated.resources.Poppins_ExtraLightItalic
import brightstart.composeapp.generated.resources.Poppins_Italic
import brightstart.composeapp.generated.resources.Poppins_Light
import brightstart.composeapp.generated.resources.Poppins_LightItalic
import brightstart.composeapp.generated.resources.Poppins_Medium
import brightstart.composeapp.generated.resources.Poppins_MediumItalic
import brightstart.composeapp.generated.resources.Poppins_Regular
import brightstart.composeapp.generated.resources.Poppins_SemiBold
import brightstart.composeapp.generated.resources.Poppins_SemiBoldItalic
import brightstart.composeapp.generated.resources.Poppins_Thin
import brightstart.composeapp.generated.resources.Poppins_ThinItalic
import brightstart.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
fun Typography(): Typography {
    val poppins = FontFamily(
        Font(Res.font.Poppins_Thin, FontWeight.Thin, FontStyle.Normal),
        Font(Res.font.Poppins_ThinItalic, FontWeight.Thin, FontStyle.Italic),
        Font(Res.font.Poppins_ExtraLight, FontWeight.ExtraLight, FontStyle.Normal),
        Font(Res.font.Poppins_ExtraLightItalic, FontWeight.ExtraLight, FontStyle.Italic),
        Font(Res.font.Poppins_Light, FontWeight.Light, FontStyle.Normal),
        Font(Res.font.Poppins_LightItalic, FontWeight.Light, FontStyle.Italic),
        Font(Res.font.Poppins_Regular, FontWeight.Normal, FontStyle.Normal),
        Font(Res.font.Poppins_Italic, FontWeight.Normal, FontStyle.Italic),
        Font(Res.font.Poppins_Medium, FontWeight.Medium, FontStyle.Normal),
        Font(Res.font.Poppins_MediumItalic, FontWeight.Medium, FontStyle.Italic),
        Font(Res.font.Poppins_SemiBold, FontWeight.SemiBold, FontStyle.Normal),
        Font(Res.font.Poppins_SemiBoldItalic, FontWeight.SemiBold, FontStyle.Italic),
        Font(Res.font.Poppins_Bold, FontWeight.Bold, FontStyle.Normal),
        Font(Res.font.Poppins_BoldItalic, FontWeight.Bold, FontStyle.Italic),
        Font(Res.font.Poppins_ExtraBold, FontWeight.ExtraBold, FontStyle.Normal),
        Font(Res.font.Poppins_ExtraBoldItalic, FontWeight.ExtraBold, FontStyle.Italic),
        Font(Res.font.Poppins_Black, FontWeight.Black, FontStyle.Normal),
        Font(Res.font.Poppins_BlackItalic, FontWeight.Black, FontStyle.Italic),
    )

    return Typography(
        displayLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 56.sp,
            lineHeight = 56.sp,
        ),
        displayMedium = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 46.sp,
            lineHeight = 46.sp,
        ),
        displaySmall = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 36.sp,
            lineHeight = 36.sp,
        ),
        titleLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 30.sp,
            lineHeight = 30.sp,
        ),
        titleMedium = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Medium,
            fontSize = 24.sp,
            lineHeight = 24.sp,
        ),
        titleSmall = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 24.sp,
        ),
        bodyLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            letterSpacing = 0.12.sp,
            lineHeight = 33.sp,
        ),
        bodyMedium = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            letterSpacing = 0.1.sp,
            lineHeight = 24.sp,
        ),
        bodySmall = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 14.sp,
            letterSpacing = 0.12.sp,
            lineHeight = 21.sp,
        ),
        labelLarge = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 20.sp,
            letterSpacing = 0.1.sp,
            lineHeight = 24.sp,
        ),
        labelMedium = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 18.sp,
            letterSpacing = 0.1.sp,
            lineHeight = 24.sp,
        ),
        labelSmall = TextStyle(
            fontFamily = poppins,
            fontWeight = FontWeight.SemiBold,
            fontSize = 16.sp,
            letterSpacing = 0.12.sp,
            lineHeight = 21.sp,
        )
    )
}
