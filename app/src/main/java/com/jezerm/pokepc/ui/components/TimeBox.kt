package com.jezerm.pokepc.ui.components

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.data.RoomRepository
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun TimeBox() {

    val grayColor = Color(198, 198, 198)
    val timeStart = remember { mutableStateOf<Date?>(null) }
    val timerValue = remember { mutableStateOf("00:00") }
    val handler = Handler(Looper.getMainLooper())
    val dateFormat = SimpleDateFormat("mm:ss", Locale.getDefault())
    val repository = RoomRepository.getInstance()
    val scope = rememberCoroutineScope()

    fun updateTimer() {
        handler.postDelayed({
            val elapsedTime = System.currentTimeMillis() - timeStart.value!!.time
            val formattedTime = dateFormat.format(Date(elapsedTime))
            timerValue.value = formattedTime
            updateTimer()
        }, 1000)
    }

    DisposableEffect(rememberSystemUiController()) {
        scope.launch(Dispatchers.IO) {
            repository.getTimer()?.let { time ->
                if (time.timeEnd != null) {
                    val elapsedTime = time.timeEnd!!.time - time.timeStart!!.time
                    timerValue.value = dateFormat.format(elapsedTime)
                } else {
                    timeStart.value = time.timeStart
                    updateTimer()
                }
            } ?: run {
                repository.startTimer()
                timeStart.value = Date()
                updateTimer()
            }

        }
        onDispose { }
    }

    Surface(modifier = Modifier.layoutId("timerBox")) {
        Card(
            modifier = Modifier
                .clip(RectangleShape)
                .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
            shape = RectangleShape,
            backgroundColor = grayColor
        ) {
            Column(
                modifier = Modifier
                    .background(Color(143, 143, 143))
                    .padding(24.dp, 16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextShadow(
                    modifier = Modifier,
                    text = timerValue.value,
                    MaterialTheme.typography.h3,
                    TextAlign.Center
                )
            }
        }
    }
}