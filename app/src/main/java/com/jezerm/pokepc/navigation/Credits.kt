package com.jezerm.pokepc.navigation

import android.os.Build
import android.view.WindowInsetsController
import androidx.compose.animation.core.AnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.SpringSpec
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.OverscrollConfiguration
import androidx.compose.foundation.OverscrollEffect
import androidx.compose.foundation.ScrollState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.animateScrollBy
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.overscroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.data.RoomRepository
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun Credits(navController: NavController) {
//    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    val coroutineScope = rememberCoroutineScope()
    val height = remember {mutableStateOf(0f)}
    val scrollState = rememberScrollState()
    val systemUiController = rememberSystemUiController()
    val completionTime = remember {mutableStateOf("00:00")}
    val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())


    DisposableEffect(systemUiController) {
        coroutineScope.launch(Dispatchers.IO){
            val repository = RoomRepository.getInstance()
            val time = repository.getTimer()
            if(time.timeEnd == null) {
                repository.endTimer()
                time.timeEnd = Date()
            }
            val elapsedTime = time.timeEnd!!.time - time.timeStart!!.time
            completionTime.value = dateFormat.format(elapsedTime)
            scrollState.animateScrollBy(2000f, tween(20000, 300, LinearEasing))
        }
        onDispose { }
    }

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
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(scrollState)
                    .onGloballyPositioned { coordinates ->
                        height.value = coordinates.size.height.toFloat()
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.height(150.dp))
                TextShadow(text = "ARCraft", style = MaterialTheme.typography.h1)
                Spacer(modifier = Modifier.height(150.dp))
                TextShadow(
                    text = "Completado en ${completionTime.value}",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(150.dp))
                TextShadow(
                    text = "Creado por",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
                // Integrantes
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Leo Corea",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Martín Majewsky",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Jezer Mejía",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Armando Meza",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(150.dp))
                TextShadow(
                    text = "Agradecimientos",
                    style = MaterialTheme.typography.h2,
                    textAlign = TextAlign.Center
                )
                // Integrantes
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Coordinador Armando López",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Prof. José Durán",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Prof. Noyra Rodríguez",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(50.dp))
                TextShadow(
                    text = "Prof. Harold Miranda",
                    style = MaterialTheme.typography.h3,
                    textAlign = TextAlign.Center
                )

                Spacer(modifier = Modifier.height(100.dp))

                BorderedButton(
                    onClick = {
                        navController.navigate("Home")
                    }
                ) {
                    TextShadow(text = "Volver al inicio", style = MaterialTheme.typography.h3)
                }
                Spacer(modifier = Modifier.height(150.dp))
            }
        }
    }
}
