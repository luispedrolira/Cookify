package com.luispedrolira.cookify.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.luispedrolira.cookify.R

val LibreBaskerville = FontFamily(
    Font(R.font.libre_baskerville_bold, FontWeight.Bold),
    Font(R.font.libre_baskerville_italic, FontWeight.Light),
    Font(R.font.libre_baskerville_regular, FontWeight.Normal),
)

val TitleCard = FontFamily(Font(R.font.libre_baskerville_bold, FontWeight.Bold))

// Set of Material typography styles to start with
val CookifyTipography = Typography(
    titleLarge = TextStyle(
        fontFamily = LibreBaskerville,
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = LibreBaskerville,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    ),
    labelLarge = TextStyle(
        fontFamily = LibreBaskerville,
        fontWeight = FontWeight.Light,
        fontSize = 12.sp
    ),
    displayMedium = TextStyle(
        fontFamily = LibreBaskerville,
        fontWeight = FontWeight.Bold,
        fontSize =  40.sp
    ),
    displayLarge = TextStyle(
        fontFamily = LibreBaskerville,
        fontWeight = FontWeight.Bold,
        fontSize =  48.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)