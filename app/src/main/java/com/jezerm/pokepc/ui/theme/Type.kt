package com.jezerm.pokepc.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.jezerm.pokepc.R

val unifont = FontFamily(
    Font(R.font.unifont15),
)

// Set of Material typography styles to start with
val Typography = Typography(
    defaultFontFamily = unifont,
    body1 = TextStyle(
        fontWeight = FontWeight.W500,
        fontSize = 16.sp
    ),
    button = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = 16.sp
    ),
    h1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 52.sp,
    ),
    h2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 32.sp,
    ),
    h3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = 24.sp,
    )
/* Other default text styles to override
caption = TextStyle(
    fontFamily = FontFamily.Default,
    fontWeight = FontWeight.Normal,
    fontSize = 12.sp
)
*/
)