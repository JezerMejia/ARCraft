package com.jezerm.pokepc.entities

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import com.jezerm.pokepc.data.ItemDto

class InventoryUpdater(val playerInventory: Inventory, val inventoryTwo: Inventory) {
    val updateCounter = mutableStateOf(0)
    val initialSelection = mutableStateOf<ItemDto?>(null)

    fun moveItemFromInventoryToInventory(
        endSelection: ItemDto,
        moveAll: Boolean = true,
        replace: Boolean = true
    ) {
        if (initialSelection.value == null) {
            initialSelection.value = if (endSelection.item != Item.AIR) endSelection else null
            return
        }
        val initSelection = initialSelection.value!!

        if (initSelection.inventoryId == endSelection.inventoryId) {
            // Get current inventory
            val inv = if (endSelection.inventoryId == inventoryTwo.getId()) {
                inventoryTwo
            } else {
                playerInventory
            }
            val result = inv.moveItem(initSelection.position, endSelection.position)
            Log.d("InventoryUpdater", "Moved same: $result - ${inv.items.toList()}")
        } else {
            // Get inventories according to the selected items' inventory
            val initInv = if (initSelection.inventoryId == playerInventory.getId()) {
                playerInventory
            } else {
                inventoryTwo
            }
            val endInv = if (endSelection.inventoryId == inventoryTwo.getId()) {
                inventoryTwo
            } else {
                playerInventory
            }

            val quantity = if (moveAll) initSelection.quantity else 1
            val result = initInv.moveItemToInventory(
                endInv,
                initSelection.position,
                quantity,
                endSelection.position,
                replace
            )
            Log.d("InventoryUpdater", "Moved to other: $result - $quantity)}")
        }

        updateCounter.value++
        Log.d("InventoryUpdater", "Counter: ${updateCounter.value}")
        initialSelection.value = null
    }
}