package com.example.sbi.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.sbi.R

val Robottocondensed = FontFamily(
    Font(R.font.robotocondensed_light),
    Font(R.font.robotocondensed_bold),
    Font(R.font.robotocondensed_regular),
)

// Set of Material typography styles to start with
val Typography = Typography(
    bodySmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = White
    ),
    bodyMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = White
    ),
    bodyLarge = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp,
        color = White
    ),
    titleSmall = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp,
        color = White
    ),
    titleMedium = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = 20.sp,
        lineHeight = 26.sp,
        letterSpacing = 0.sp,
        color = White
    ),
    titleLarge = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 42.sp,
        letterSpacing = 0.sp,
        color = White
    ),
    labelSmall = TextStyle(
        fontFamily = Robottocondensed,
        fontWeight = FontWeight.Light,
        fontSize = 30.sp,
        color = White
    ),
    labelMedium = TextStyle(
        fontFamily = Robottocondensed,
        fontWeight = FontWeight.Light,
        fontSize = 25.sp,
        color = White
    ),
    labelLarge = TextStyle(
        fontFamily = Robottocondensed,
        fontWeight = FontWeight.Light,
        fontSize = 40.sp,
        color = White
    ),
)
