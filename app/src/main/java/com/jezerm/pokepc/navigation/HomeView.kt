package com.jezerm.pokepc.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.jezerm.pokepc.ui.theme.PokePCTheme

@Composable
fun HomeView(controller: NavHostController) {
    Column {
        Text("PokeCraft", style = MaterialTheme.typography.h1)
        Text("Un grandioso juego", style = MaterialTheme.typography.h2)
        Text("Y pues eso, tienes que craftear muchas cosas. Habrá múltiples recetas de crafteo en la pantalla. Tendrás que elegir cualquiera y posteriormente buscar todos los materiales en la sala para poder craftear el elemento.")
        Button(onClick = {
            controller.navigate("poke")
        }) {
            Text("Abrir AR", style = MaterialTheme.typography.button)
        }
        Button(onClick = {
            controller.navigate("poke")
        }) {
            Text("Abrir AR")
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