package com.jezerm.pokepc.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp

class PixelBorderShape(val borderSize: Dp = 4.dp) : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density
    ): Outline {
        val path = Path().apply {
            val px = with(density) { borderSize.toPx() }

            lineTo(0f, px)
            lineTo(px, px)
            lineTo(px, 0f)

            lineTo(size.width - px, 0f)
            lineTo(size.width - px, px)
            lineTo(size.width, px)

            lineTo(size.width, size.height - px)
            lineTo(size.width - px, size.height - px)
            lineTo(size.width - px, size.height)

            lineTo(px, size.height)
            lineTo(px, size.height - px)
            lineTo(0f, size.height - px)
            close()
        }
        return Outline.Generic(path)
    }
}

val Shapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(4.dp),
    large = RoundedCornerShape(0.dp)
)