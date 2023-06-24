package com.jezerm.pokepc.dialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.R
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun ChestInventoryDialog(setShowDialog: (Boolean) -> Unit, chestType: Chest.ChestType) {
    val scope = rememberCoroutineScope()

    val chest by remember { mutableStateOf(Chest(chestType)) }
    val chestItems = remember { mutableStateListOf<ItemDto>() }

    val inventory by remember { mutableStateOf(Inventory()) }
    val inventoryItems = remember { mutableStateListOf<ItemDto>() }

    var updateCounter by remember { mutableStateOf(0) }
    var initialSelection by remember { mutableStateOf<ItemDto?>(null) }

    LaunchedEffect(updateCounter) {
        Log.d("Chest", "Chest was modified: ${chest.items.toList()}")

        scope.launch(Dispatchers.IO) {
            val list = arrayListOf<ItemDto>()
            for (i in 1..chest.size) {
                val item = chest.items.find { v -> v.position == i } ?: ItemDto(
                    Item.AIR,
                    0,
                    i,
                    chest.getId()
                )
                Log.d("Chest", "Position: $i, Item: ${item.item.value} - ${item.quantity}")
                list.add(item)
            }
            chestItems.clear()
            chestItems.addAll(list)
        }
    }
    LaunchedEffect(updateCounter) {
        Log.d("Inventory", "Inventory was modified: ${inventory.items.toList()}")

        scope.launch(Dispatchers.IO) {
            val list = arrayListOf<ItemDto>()
            for (i in 1..inventory.size) {
                val item = inventory.items.find { v -> v.position == i } ?: ItemDto(
                    Item.AIR,
                    0,
                    i,
                    inventory.getId()
                )
                Log.d("Inventory", "Position: $i, Item: ${item.item.value} - ${item.quantity}")
                list.add(item)
            }
            inventoryItems.clear()
            inventoryItems.addAll(list)
        }
    }

    DisposableEffect(rememberSystemUiController()) {
        for (i in 1..chest.size) {
            chestItems.add(ItemDto(Item.AIR, 0, i, chest.getId()))
        }
        for (i in 1..inventory.size) {
            inventoryItems.add(ItemDto(Item.AIR, 0, i, inventory.getId()))
        }

        scope.launch(Dispatchers.IO) {
            chest.initFromDatabase()
            inventory.initFromDatabase()

        }
        onDispose { }
    }

    fun moveItem(itemDto: ItemDto) {
        if (initialSelection == null) {
            initialSelection = if (itemDto.item != Item.AIR) itemDto else null
            return
        }
        val endSelection = itemDto
        val initSelection = initialSelection!!

        if (initSelection.inventoryId == endSelection.inventoryId) {
            val inv = if (endSelection.inventoryId == chest.getId()) chest else inventory
            val result = inv.moveItem(initSelection.item, endSelection.position)
            Log.d("Chest", "Moved: $result - ${inv.items.toList()}")
        } else {
            val initInv = if (initSelection.inventoryId == chest.getId()) chest else inventory
            val endInv = if (endSelection.inventoryId == chest.getId()) chest else inventory
            val result = initInv.moveItemToInventory(
                endInv,
                initSelection.item,
                initSelection.quantity,
                endSelection.position
            )
            Log.d("Chest", "Moved: $result)}")
        }

        updateCounter++
        initialSelection = null
    }

    val grayColor = Color(198, 198, 198)
    val blueColor = Color(136, 146, 201)

    Dialog(onDismissRequest = {
        scope.launch(Dispatchers.IO) {
            chest.saveToDatabase()
            inventory.saveToDatabase()
            Log.d("Inventory", "Save data")
        }
        setShowDialog(false)
    }) {
        Surface {
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                shape = RectangleShape,
                backgroundColor = grayColor
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        val chestImg = when (chestType) {
                            Chest.ChestType.ONE -> R.drawable.chest
                            Chest.ChestType.TWO -> R.drawable.ender_chest
                            Chest.ChestType.THREE -> R.drawable.xmas_chest
                        }

                        Image(
                            painter = painterResource(id = chestImg),
                            contentDescription = "",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.size(200.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(
                            text = "Cofre",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier
                                .widthIn(100.dp, 164.dp)
                                .heightIn(100.dp, 164.dp),
                            columns = GridCells.Fixed(4),
                            userScrollEnabled = false
                        ) {
                            items(chestItems, key = { c -> c.position }) { itemDto ->
                                val item = itemDto.item
                                val quantity = itemDto.quantity
                                val imageBitmap = ImageBitmap.imageResource(item.image)
                                val interactionSource = remember { MutableInteractionSource() }
                                Surface(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rememberRipple(
                                                bounded = true,
                                                color = blueColor
                                            ),
                                            onClick = {
                                                moveItem(itemDto)
                                            }
                                        ),
                                    color = if (initialSelection?.item == item) Color(
                                        94,
                                        94,
                                        94
                                    )
                                    else Color(139, 139, 139)
                                ) {
                                    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .clip(RectangleShape)
                                                .insetBorder(
                                                    lightSize = 4.dp,
                                                    darkSize = 4.dp,
                                                    borderPadding = 0.dp
                                                )
                                                .padding(4.dp),
                                            bitmap = imageBitmap,
                                            filterQuality = FilterQuality.None,
                                            contentDescription = item.value,
                                            contentScale = ContentScale.FillWidth,
                                            alignment = Alignment.Center
                                        )
                                        if (item != Item.AIR) {
                                            TextShadow(
                                                modifier = Modifier.padding(
                                                    end = 3.dp,
                                                    bottom = 3.dp
                                                ),
                                                text = quantity.toString(),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }

                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(
                            text = "Inventario",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {

                        LazyVerticalGrid(
                            modifier = Modifier
                                .widthIn(100.dp, 224.dp)
                                .heightIn(100.dp, 180.dp),
                            columns = GridCells.Fixed(5),
                            userScrollEnabled = false
                        ) {
                            items(inventoryItems, key = { c -> c.position }) { itemDto ->
                                val item = itemDto.item
                                val quantity = itemDto.quantity
                                val imageBitmap = ImageBitmap.imageResource(item.image)
                                val interactionSource = remember { MutableInteractionSource() }
                                Surface(
                                    modifier = Modifier
                                        .clickable(
                                            interactionSource = interactionSource,
                                            indication = rememberRipple(
                                                bounded = true,
                                                color = blueColor
                                            ),
                                            onClick = {
                                                moveItem(itemDto)
                                            }
                                        ),
                                    color = if (initialSelection?.item == item) Color(
                                        94,
                                        94,
                                        94
                                    )
                                    else Color(139, 139, 139)
                                ) {
                                    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
                                        Image(
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .wrapContentHeight()
                                                .clip(RectangleShape)
                                                .insetBorder(
                                                    lightSize = 4.dp,
                                                    darkSize = 4.dp,
                                                    borderPadding = 0.dp
                                                )
                                                .padding(4.dp),
                                            bitmap = imageBitmap,
                                            filterQuality = FilterQuality.None,
                                            contentDescription = item.value,
                                            contentScale = ContentScale.FillWidth,
                                            alignment = Alignment.Center
                                        )
                                        if (item != Item.AIR) {
                                            TextShadow(
                                                modifier = Modifier.padding(
                                                    end = 3.dp,
                                                    bottom = 3.dp
                                                ),
                                                text = quantity.toString(),
                                                fontWeight = FontWeight.Bold
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
    }
}