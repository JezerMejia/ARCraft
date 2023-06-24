package com.jezerm.pokepc.dialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun InventoryDialog(setShowDialog: (Boolean) -> Unit) {
    val scope = rememberCoroutineScope()

    val inventory by remember { mutableStateOf(Inventory()) }
    val inventoryItems = remember { mutableStateListOf<ItemDto>() }

    var updateCounter by remember { mutableStateOf(0) }
    var initialSelection by remember { mutableStateOf<ItemDto?>(null) }

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
        for (i in 1..inventory.size) {
            inventoryItems.add(ItemDto(Item.AIR, 0, i, inventory.getId()))
        }
        scope.launch(Dispatchers.IO) {
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

        val inv = inventory
        val result = inv.moveItem(initSelection.item, endSelection.position)
        Log.d("Chest", "Moved: $result - ${inv.items.toList()}")

        updateCounter++
        initialSelection = null
    }

    val blueColor = Color(136, 146, 201)
    val grayColor = Color(198, 198, 198)

    Dialog(
        onDismissRequest = {
            scope.launch(Dispatchers.IO) {
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
                                .widthIn(100.dp, 200.dp)
                                .heightIn(260.dp, 270.dp),
                            columns = GridCells.Fixed(4),
                            userScrollEnabled = false
                        ) {
                            items(inventoryItems, key = { c -> c.position }) { itemDto ->
                                val item = itemDto.item
                                val quantity = itemDto.quantity
                                val position = itemDto.position
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
                                                .insetBorder(lightSize = 4.dp, darkSize = 4.dp, borderPadding = 0.dp)
                                                .padding(4.dp),
                                            bitmap = imageBitmap,
                                            filterQuality = FilterQuality.None,
                                            contentDescription = item.value,
                                            contentScale = ContentScale.FillWidth,
                                            alignment = Alignment.Center
                                        )
                                        if (item != Item.AIR) {
                                            TextShadow(
                                                modifier = Modifier.padding(end = 3.dp, bottom = 3.dp),
                                                text = quantity.toString(),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(
                            text = "Hotbar",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}