package com.jezerm.pokepc.dialog

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.InventoryUpdater
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun InventoryGrid(inventory: Inventory, updater: InventoryUpdater) {
    val scope = rememberCoroutineScope()
    val blueColor = Color(136, 146, 201)

    val inventoryItems = remember { mutableStateListOf<ItemDto>() }

    LaunchedEffect(updater.updateCounter.value) {
        Log.d("Inventory", "Inventory was modified: ${inventory.items.toList()}")

        val list = mutableStateListOf<ItemDto>()
        for (i in 1..inventory.size) {
            val item = inventory.items.find { v -> v.position == i } ?: ItemDto(
                Item.AIR,
                0,
                i,
                inventory.getId()
            )
            Log.d("Inventory", "Position: $i, Item: ${item.item.value} - ${item.quantity}")
            list.add(item.copy())
        }
        inventoryItems.clear()
        inventoryItems.addAll(list)
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

    LazyVerticalGrid(
        modifier = Modifier
            .widthIn(100.dp, 224.dp)
            .heightIn(100.dp, 200.dp),
        columns = GridCells.Fixed(5),
        userScrollEnabled = false
    ) {
        items(inventoryItems, key = { c -> c.position }) { itemDto ->
            val item = itemDto.item
            val quantity = itemDto.quantity
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
                            updater.moveItemFromInventoryToInventory(itemDto, true, true)
                        }
                    ),
                color = if (isSelected) Color(94, 94, 94)
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
                        bitmap = ImageBitmap.imageResource(item.image),
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

