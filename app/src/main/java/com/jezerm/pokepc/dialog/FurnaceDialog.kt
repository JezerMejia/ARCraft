package com.jezerm.pokepc.dialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.*
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
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.entities.Furnace
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.InventoryUpdater
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.BorderedButton
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
private fun BoxScope.FurnaceSlot(furnace: Furnace, updater: InventoryUpdater) {
    val blueColor = Color(136, 146, 201)

    val defaultItem = ItemDto(Item.AIR, 0, 1, furnace.getId())
    var furnaceItem by remember { mutableStateOf(defaultItem) }

    LaunchedEffect(updater.updateCounter.value) {
        val item = furnace.items.firstOrNull() ?: ItemDto(
            Item.AIR,
            0,
            1,
            furnace.getId()
        )
        furnaceItem = item.copy()
    }

    val item = furnaceItem.item
    val interactionSource = remember { MutableInteractionSource() }
    val selected = updater.initialSelection.value
    val isSelected = selected == furnaceItem

    Surface(
        modifier = Modifier
            .offset(y = 42.dp)
            .align(Alignment.TopCenter)
            .size(48.dp)
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(
                    bounded = true,
                    color = blueColor
                ),
                onClick = {
                    updater.moveItemFromInventoryToInventory(furnaceItem, false, false)
                }
            ),
        color = if (isSelected) Color(94, 94, 94)
        else Color(139, 139, 139)
    ) {
        Image(
            modifier = Modifier
                .fillMaxSize()
                .clip(RectangleShape)
                .insetBorder(lightSize = 4.dp, darkSize = 4.dp, borderPadding = 0.dp)
                .padding(4.dp),
            bitmap = ImageBitmap.imageResource(item.image),
            filterQuality = FilterQuality.None,
            contentDescription = item.value,
            contentScale = ContentScale.FillWidth,
            alignment = Alignment.Center
        )
    }
}

@Composable
fun FurnaceDialog(setShowDialog: (Boolean) -> Unit) {
    val scope = rememberCoroutineScope()
    val grayColor = Color(198, 198, 198)

    val furnace by remember { mutableStateOf(Furnace()) }
    val inventory by remember { mutableStateOf(Inventory()) }

    val inventoryUpdater by remember { mutableStateOf(InventoryUpdater(inventory, furnace)) }

    Dialog(
        onDismissRequest = {
            furnace.moveAllItemsToInventory(inventory)
            scope.launch(Dispatchers.IO) {
                inventory.saveToDatabase()
                Log.d("Inventory", "Save data")
            }
            setShowDialog(false)
        }
    ) {
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
                                text = "Horno",
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
                                FurnaceSlot(furnace, inventoryUpdater)
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
                                    val item = furnace.craftRecipe()
                                        ?: return@BorderedButton
                                    furnace.items.clear()
                                    inventory.addItem(item)
                                    inventoryUpdater.forceUpdate()
                                }
                            ) {
                                TextShadow(
                                    text = "Hornear",
                                    style = MaterialTheme.typography.button
                                )
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
                            InventoryGrid(inventory, inventoryUpdater)
                        }
                    }
                }
            }
        }
    }
}