package com.jezerm.pokepc.navigation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.theme.PixelBorderShape
import com.jezerm.pokepc.ui.theme.PokePCTheme

@Composable
fun RecipeCards() {

}

@Composable
fun HomeView(controller: NavHostController) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Transparent,
        contentColor = MaterialTheme.colors.onSurface,
    ) { innerPadding ->
        Surface(Modifier.padding(innerPadding), color = Color.Transparent) {
            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(16.dp)) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    TextShadow("ARCraft", style = MaterialTheme.typography.h1)
                    TextShadow(
                        "Minecraft en Realidad Aumentada",
                        style = MaterialTheme.typography.h3,
                        textAlign = TextAlign.Center
                    )
                }
                TextShadow("Hola")
                OutlinedButton(
                    modifier = Modifier.fillMaxWidth(),
                    shape = PixelBorderShape(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(143, 143, 143)),
                    border = BorderStroke(2.dp, Color.Black),
                    onClick = {
                        controller.navigate("poke")
                    }
                ) {
                    TextShadow("Abrir AR", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomePreview() {
    PokePCTheme {
        HomeView(rememberNavController())
    }
}