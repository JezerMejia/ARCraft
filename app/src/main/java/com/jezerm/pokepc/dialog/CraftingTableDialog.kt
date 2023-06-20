package com.jezerm.pokepc.dialog

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jezerm.pokepc.R
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.theme.PixelBorderShape

@Composable
fun CraftingTableDialog(setShowDialog: (Boolean) -> Unit) {

    val data = remember { mutableStateOf(List(20) { "Item $it" }) }

    val craftingTableGridBoxModifier = Modifier
        .size(40.dp)
        .background(color = Color.Transparent)
        .border(2.dp, Color(85, 85, 85))

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface(
            shape = PixelBorderShape(),
            color = Color(143, 143, 143)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(text = "Mesa de Crafteo", MaterialTheme.typography.h3)
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .size(210.dp)
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.crafting_table_top),
                                contentDescription = "",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                            Column(
                                modifier = Modifier
                                    .align(Alignment.Center)
                            ) {
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                }
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center
                                ) {
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                    Box(modifier = craftingTableGridBoxModifier)
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        OutlinedButton(
                            shape = PixelBorderShape(),
                            colors = ButtonDefaults.buttonColors(backgroundColor = Color(143, 143, 143)),
                            border = BorderStroke(2.dp, Color.Black),
                            onClick = {

                            }
                        ) {
                            TextShadow("Craftear", style = MaterialTheme.typography.button)
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(text = "Inventario", style = MaterialTheme.typography.h3)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                        ) {
                            items(data.value.size) { index ->
                                Box (
                                    modifier = Modifier
                                        .aspectRatio(1f)
                                        .background(Color(143, 143, 143))
                                        .border(2.dp, Color(85, 85, 85))
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}