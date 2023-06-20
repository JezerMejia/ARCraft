package com.jezerm.pokepc.ui.theme

import android.graphics.Bitmap
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.res.imageResource
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun PokePCTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = DarkColorPalette

    val systemUiController = rememberSystemUiController()
    systemUiController.setSystemBarsColor(
        color = Color.Transparent
    )

    val image = ImageBitmap.imageResource(com.jezerm.pokepc.R.drawable.light_dirt_background)
    val bitmap = Bitmap.createScaledBitmap(image.asAndroidBitmap(), 120, 120, false)
    val finalImage = bitmap.asImageBitmap()


    Surface(Modifier.fillMaxSize(), color = Color.White) {
        Canvas(
            modifier = Modifier.fillMaxSize()
        ) {

            val paint = Paint().asFrameworkPaint().apply {
                isAntiAlias = true
                shader = ImageShader(finalImage, TileMode.Repeated, TileMode.Repeated)
            }

            drawIntoCanvas {
                it.nativeCanvas.drawPaint(paint)
            }
            paint.reset()
        }

        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content,
        )
    }
}