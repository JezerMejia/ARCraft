package com.jezerm.pokepc.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController

@Composable
fun HomeView(controller: NavHostController) {
    Column {
        Text("Home")
        Button(onClick = {
            controller.navigate("poke")
        }) {
            Text("Abrir AR")
        }
    }
}