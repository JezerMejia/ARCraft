package com.jezerm.pokepc.navigation

import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.R
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import com.jezerm.pokepc.ui.theme.PokePCTheme

@Composable
fun HowToView(controller: NavHostController) {
    val scaffoldState = rememberScaffoldState()

    val systemUiController = rememberSystemUiController()
    DisposableEffect(systemUiController) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            systemUiController.systemBarsBehavior =
                WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
        }
        systemUiController.setStatusBarColor(color = Color(0, 0, 0, 100))
        onDispose { }
    }

    val imageBorderModifier = Modifier
        .clip(RectangleShape)
        .border(
            BorderStroke(2.dp, Color.Black)
        )
        .outsetBorder(darkSize = 6.dp, lightSize = 4.dp, borderPadding = 2.dp)

    Scaffold(
        modifier = Modifier
            .fillMaxSize(),
        scaffoldState = scaffoldState,
        topBar = {
            Surface(
                Modifier
                    .fillMaxWidth()
                    .background(Color(0, 0, 0, 100))
                    .displayCutoutPadding()
            ) {
            }
        },
        backgroundColor = Color(0, 0, 0, 70),
        contentColor = MaterialTheme.colors.onSurface,
    ) { innerPadding ->
        Surface(Modifier.padding(innerPadding), color = Color.Transparent) {
            Box {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    TextShadow(
                        modifier = Modifier
                            .padding(top = 42.dp)
                            .fillMaxWidth(),
                        text = "Tutorial",
                        style = MaterialTheme.typography.h1,
                        textAlign = TextAlign.Center
                    )
                    TextShadow(
                        text = "¿Qué es Minecraft?",
                        style = MaterialTheme.typography.h3,
                    )
                    TextShadow(
                        text = "Por si no lo conocías, Minecraft es un juego muy popular de género Sandbox " +
                                "en el que puedes hacer lo que desees. Puedes jugar el modo Supervivencia en " +
                                "el que tendrás que sobrevivir a su mundo, o bien en el modo Creativo con el " +
                                "cual podrás dar rienda suelta a tu creatividad."
                    )
                    Image(
                        bitmap = ImageBitmap.imageResource(R.drawable.minecraft_cherry_sunset),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                            .then(imageBorderModifier),
                        contentDescription = "Atardecer en Minecraft en un bioma de Cerezos",
                        contentScale = ContentScale.FillWidth
                    )
                    TextShadow(
                        text = "Cómo jugar ARCraft",
                        style = MaterialTheme.typography.h3,
                    )
                    TextShadow(
                        text = "Este juego usa Realidad Aumentada (AR) para mostrar diversos elementos " +
                                "interactivos, como lo pueden ser cofres, mesas de crafteos, hornos, " +
                                "y algunos cuantos animales."
                    )
                    TextShadow(
                        text = "Tu objetivo será completar las 2 recetas (o crafteos) que están en el " +
                                "menú principal: Un Pastel y un Beacon."
                    )
                    TextShadow(
                        text = "Cómo obtener los ingredientes",
                        style = MaterialTheme.typography.h4,
                    )
                    TextShadow(
                        text = "Busca un cofre, apúntalo con la cámara y toca el modelo 3D del " +
                                "cofre para abrirlo. Dentro encontrarás varios ingredientes."
                    )
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            modifier = Modifier
                                .then(imageBorderModifier)
                                .fillMaxWidth()
                                .weight(1f),
                            bitmap = ImageBitmap.imageResource(R.drawable.screenshot_chest_no_model),
                            contentDescription = "Cámara apuntando a una imagen de un cofre"
                        )
                        Image(
                            modifier = Modifier
                                .then(imageBorderModifier)
                                .fillMaxWidth()
                                .weight(1f),
                            bitmap = ImageBitmap.imageResource(R.drawable.screenshot_chest_model),
                            contentDescription = "Cámara apuntando al modelo 3D del cofre"
                        )
                        Image(
                            modifier = Modifier
                                .then(imageBorderModifier)
                                .fillMaxWidth()
                                .weight(1f),
                            bitmap = ImageBitmap.imageResource(R.drawable.screenshot_chest_open),
                            contentDescription = "Interfaz del cofre"
                        )
                    }
                }
                BorderedButton(
                    modifier = Modifier.offset(8.dp, 8.dp),
                    onClick = {
                        controller.popBackStack()
                    }
                ) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        bitmap = ImageBitmap.imageResource(R.drawable.back),
                        contentDescription = "Atrás",
                        filterQuality = FilterQuality.None,
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.Center
                    )
                    TextShadow(text = "Atrás", style = MaterialTheme.typography.button)
                }
            }

        }
    }
}

@Preview(showBackground = true)
@Composable
fun HowToPreview() {
    PokePCTheme {
        HowToView(rememberNavController())
    }
}
