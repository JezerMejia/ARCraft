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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jezerm.pokepc.R
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import com.jezerm.pokepc.ui.theme.PixelBorderShape

@Composable
private fun InventoryGrid() {
    val items = ArrayList<Pair<Item, Int>>()

    for (i in 1..20) {
        items.add(Item.AIR to i)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .widthIn(100.dp, 224.dp)
            .heightIn(100.dp, 200.dp),
        columns = GridCells.Fixed(5),
        userScrollEnabled = false
    ) {
        items(items, key = { c -> c.second }) { (item, position) ->
            val imageBitmap = ImageBitmap.imageResource(item.image)
            Surface(color = Color(139, 139, 139)) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RectangleShape)
                        .insetBorder(lightSize = 4.dp, darkSize = 4.dp, borderPadding = 0.dp)
                        .padding(4.dp),
                    bitmap = imageBitmap,
                    filterQuality = FilterQuality.None,
                    contentDescription = item.value,
                    contentScale = ContentScale.FillWidth,
                    alignment = Alignment.Center
                )
            }
        }
    }
}

@Composable
fun FurnaceDialog(setShowDialog: (Boolean) -> Unit) {

    val grayColor = Color(198, 198, 198)

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface {
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                shape = RectangleShape,
                backgroundColor = grayColor
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
                            TextShadow(
                                text = "Mesa de Crafteo",
                                style = MaterialTheme.typography.h3
                            )
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
                                    painter = painterResource(id = R.drawable.furnace_front),
                                    contentDescription = "",
                                    contentScale = ContentScale.Crop,
                                    modifier = Modifier.fillMaxSize()
                                )
                                val imageBitmap = ImageBitmap.imageResource(R.drawable.air)
                                Surface(
                                    modifier = Modifier
                                        .offset(y = 42.dp)
                                        .align(Alignment.TopCenter)
                                        .size(48.dp)
                                        .background(color = Color.Transparent),
                                    color = Color(139, 139, 139)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(RectangleShape)
                                            .insetBorder(
                                                lightSize = 4.dp,
                                                darkSize = 4.dp,
                                                borderPadding = 0.dp
                                            )
                                            .padding(4.dp),
                                        bitmap = imageBitmap,
                                        filterQuality = FilterQuality.None,
                                        contentDescription = "",
                                        contentScale = ContentScale.FillWidth,
                                        alignment = Alignment.Center
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Row(
                            modifier = Modifier
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            BorderedButton(
                                onClick = {

                                }
                            ) {
                                TextShadow(text = "Honear", style = MaterialTheme.typography.button)
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
                            InventoryGrid()
                        }
                    }
                }
            }
        }
    }
}