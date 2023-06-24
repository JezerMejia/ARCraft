package com.jezerm.pokepc.dialog

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.R
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.entities.CraftingTable
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
private fun CraftingGrid(craftingTable: CraftingTable, updater: InventoryUpdater) {
    val scope = rememberCoroutineScope()
    val blueColor = Color(136, 146, 201)

    val craftingItems = remember { mutableStateListOf<ItemDto>() }

    LaunchedEffect(updater.updateCounter.value) {
        Log.d("CraftingTable", "CraftingTable was modified: ${craftingTable.items.toList()}")

        scope.launch(Dispatchers.IO) {
            val list = arrayListOf<ItemDto>()
            for (i in 1..craftingTable.size) {
                val item = craftingTable.items.find { v -> v.position == i } ?: ItemDto(
                    Item.AIR,
                    0,
                    i,
                    craftingTable.getId()
                )
                Log.d("CraftingTable", "Position: $i, Item: ${item.item.value} - ${item.quantity}")
                list.add(item.copy())
            }
            craftingItems.clear()
            craftingItems.addAll(list)
        }
    }
    DisposableEffect(rememberSystemUiController()) {
        for (i in 1..craftingTable.size) {
            craftingItems.add(ItemDto(Item.AIR, 0, i, craftingTable.getId()))
        }
        onDispose { }
    }

    LazyVerticalGrid(
        modifier = Modifier
            .widthIn(100.dp, 124.dp)
            .heightIn(120.dp, 124.dp),
        columns = GridCells.Fixed(3),
        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
        verticalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterVertically),
        userScrollEnabled = false
    ) {
        items(items = craftingItems, key = { c -> c.position }) { itemDto ->
            val item = itemDto.item
            val interactionSource = remember { MutableInteractionSource() }
            val selected = updater.initialSelection.value
            val isSelected = selected == itemDto
            Surface(
                modifier = Modifier
                    .clickable(
                        interactionSource = interactionSource,
                        indication = rememberRipple(
                            bounded = true,
                            color = blueColor
                        ),
                        onClick = {
                            updater.moveItemFromInventoryToInventory(itemDto, false, false)
                        }
                    ),
                color = if (isSelected) Color(94, 94, 94)
                else Color(139, 139, 139)
            ) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
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
    }
}

@Composable
fun CraftingTableDialog(setShowDialog: (Boolean) -> Unit, context: Context) {
    val scope = rememberCoroutineScope()
    val grayColor = Color(198, 198, 198)

    val craftingTable by remember { mutableStateOf(CraftingTable()) }
    val inventory by remember { mutableStateOf(Inventory()) }

    val inventoryUpdater by remember { mutableStateOf(InventoryUpdater(inventory, craftingTable)) }

    Dialog(
        onDismissRequest = {
            craftingTable.moveAllItemsToInventory(inventory)
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
                    Column(
                        modifier = Modifier
                            .padding(24.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
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
                                    .size(240.dp)
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
                                    CraftingGrid(craftingTable, inventoryUpdater)
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
                                    val (item, quantity) = craftingTable.craftRecipe()
                                        ?: return@BorderedButton
                                    craftingTable.items.clear()
                                    inventory.addItem(item, quantity)
                                    inventoryUpdater.forceUpdate()
                                    MediaPlayer.create(context, R.raw.button_click).start()
                                }
                            ) {
                                TextShadow(
                                    text = "Craftear",
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