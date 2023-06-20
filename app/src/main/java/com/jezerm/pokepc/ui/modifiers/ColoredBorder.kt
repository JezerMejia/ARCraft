package com.jezerm.pokepc.ui.modifiers

import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private fun Modifier.coloredBorder(
    lightColor: Color,
    lightSize: Dp = 2.dp,
    darkColor: Color,
    darkSize: Dp = 3.dp,
    borderPadding: Dp = 3.dp
) =
    this.drawWithContent {
        drawContent()

        val (width, height) = size
        val padding = borderPadding.toPx()

        drawLine(
            lightColor,
            Offset(padding, height - padding),
            Offset(padding, padding),
            lightSize.toPx()
        )
        drawLine(
            lightColor,
            Offset(padding, padding),
            Offset(width - padding, padding),
            lightSize.toPx()
        )

        drawLine(
            darkColor,
            Offset(padding, height - padding),
            Offset(width - padding, height - padding),
            darkSize.toPx()
        )
        drawLine(
            darkColor,
            Offset(width - padding, height - padding),
            Offset(width - padding, padding),
            darkSize.toPx()
        )
    }

fun Modifier.outsetBorder(lightSize: Dp = 2.dp, darkSize: Dp = 3.dp, borderPadding: Dp = 3.dp) =
    this.coloredBorder(
        lightColor = Color(255, 255, 255, 100),
        darkColor = Color(0, 0, 0, 90),
        lightSize = lightSize,
        darkSize = darkSize,
        borderPadding = borderPadding
    )

fun Modifier.insetBorder(lightSize: Dp = 2.dp, darkSize: Dp = 2.dp, borderPadding: Dp = 3.dp) =
    this.coloredBorder(
        lightColor = Color(0, 0, 0, 160),
        darkColor = Color(255, 255, 255, 200),
        lightSize = lightSize,
        darkSize = darkSize,
        borderPadding = borderPadding
    )
