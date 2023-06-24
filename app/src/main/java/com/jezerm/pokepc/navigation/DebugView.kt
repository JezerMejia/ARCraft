package com.jezerm.pokepc.navigation

import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow

@OptIn(ExperimentalMaterialApi::class, ExperimentalComposeUiApi::class)
@Composable
fun DebugView(controller: NavHostController) {
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

    var showChestDialog by remember { mutableStateOf(false) }
    var showCraftingDialog by remember { mutableStateOf(false) }
    var showFurnaceDialog by remember { mutableStateOf(false) }

    var lastChestOpened by remember { mutableStateOf(Chest.ChestType.ONE) }

    if (showChestDialog)
        ChestInventoryDialog(setShowDialog = {
            showChestDialog = it
        }, lastChestOpened)

    if (showCraftingDialog)
        CraftingTableDialog(setShowDialog = {
            showCraftingDialog = it
        })

    if (showFurnaceDialog)
        FurnaceDialog(setShowDialog = {
            showFurnaceDialog = it
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
                Modifier.padding(horizontal = 16.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BorderedButton(
                    onClick = {
                        lastChestOpened = Chest.ChestType.ONE
                        showChestDialog = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #1")
                }
                BorderedButton(
                    onClick = {
                        lastChestOpened = Chest.ChestType.TWO
                        showChestDialog = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #2")
                }
                BorderedButton(
                    onClick = {
                        lastChestOpened = Chest.ChestType.THREE
                        showChestDialog = true
                    }
                ) {
                    TextShadow(text = "Abrir cofre #3")
                }
                BorderedButton(
                    onClick = {
                        showCraftingDialog = true
                    }
                ) {
                    TextShadow(text = "Abrir mesa de crafteo")
                }
                BorderedButton(
                    onClick = {
                        showFurnaceDialog = true
                    }
                ) {
                    TextShadow(text = "Abrir horno")
                }
            }
        }
    }
}
