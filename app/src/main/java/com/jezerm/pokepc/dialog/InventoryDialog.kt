package com.jezerm.pokepc.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable
import java.util.Random

@Composable
fun InventoryDialog(setShowDialog: (Boolean) -> Unit) {

    val data = remember { mutableStateOf(List(20) { "Item $it" }) }
    val state = rememberReorderableLazyGridState(onMove = { from, to ->
            data.value = data.value.toMutableList().apply {
                add(to.index, removeAt(from.index))
            }
        })

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = RoundedCornerShape(16.dp),
            color = Color.Gray
        ) {
            Box(
               contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        Text(text = "Inventario")
                    }

                    Spacer (modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ){
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            state = state.gridState,
                            modifier = Modifier
                                .reorderable(state)
                                .detectReorderAfterLongPress(state)
                        ) {
                            items(data.value.size, { it }) { item ->
                                ReorderableItem(state, key = item) {
                                    Box(
                                        modifier = Modifier
                                            .aspectRatio(1f)
                                                //Temp
                                            .background(Color(Random().nextInt(256), Random().nextInt(256), Random().nextInt(256)))
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}