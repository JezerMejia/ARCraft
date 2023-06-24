package com.jezerm.pokepc.navigation

import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DebugView(controller: NavHostController) {
    val scope = rememberCoroutineScope()
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

    val showChestDialog = remember { mutableStateOf(false) }
    val showCraftingDialog = remember { mutableStateOf(false) }
    val lastChestOpened = remember { mutableStateOf(Chest.ChestType.ONE) }

    if (showChestDialog.value)
        ChestInventoryDialog(setShowDialog = {
            showChestDialog.value = it
        }, lastChestOpened.value)

    if (showCraftingDialog.value)
        CraftingTableDialog(setShowDialog = {
            showCraftingDialog.value = it
        })

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
            Column(
                Modifier.padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                BorderedButton(
                    onClick = {
                        lastChestOpened.value = Chest.ChestType.ONE
                        showChestDialog.value = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #1")
                }
                BorderedButton(
                    onClick = {
                        lastChestOpened.value = Chest.ChestType.TWO
                        showChestDialog.value = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #2")
                }
                BorderedButton(
                    onClick = {
                        lastChestOpened.value = Chest.ChestType.THREE
                        showChestDialog.value = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #3")
                }
                BorderedButton(
                    onClick = {
                        showCraftingDialog.value = true
                    }
                ) {
                    TextShadow(text = "Abrir mesa de crafteo")
                }
            }
        }
    }
}
