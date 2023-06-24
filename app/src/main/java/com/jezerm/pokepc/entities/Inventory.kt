package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.data.RoomRepository
import kotlinx.coroutines.flow.firstOrNull

open class Inventory(val size: Int = 20, val type: InventoryType = InventoryType.PLAYER) {
    val items = arrayListOf<ItemDto>()

    open fun getId(): Int =
        when (type) {
            InventoryType.PLAYER -> 1
            else -> -1
        }

    suspend fun initFromDatabase() {
        if (type == InventoryType.NO_SAVE) return
        val repository = RoomRepository.getInstance()
        val newItems = repository.getInventory(this).firstOrNull() ?: listOf()
        items.clear()
        items.addAll(newItems)
    }

    suspend fun saveToDatabase() {
        if (type == InventoryType.NO_SAVE) return
        val repository = RoomRepository.getInstance()
        val oldItems = repository.getInventory(this).firstOrNull() ?: listOf()

        for (new in items) {
            if (oldItems.any { it.item == new.item })
                repository.update(new)
            else
                repository.insert(new)
        }
        for (old in oldItems) {
            if (!items.any { it.item == old.item })
                repository.delete(old)
        }
    }

    fun findEmptyPosition(): Int {
        for (i in 1..size) {
            if (items.find { itemDto -> itemDto.position == i } == null)
                return i
        }
        return -1
    }

    fun hasItem(item: Item): Boolean = items.any { itemDto -> itemDto.item == item }

    fun addItem(item: Item, quantity: Int = 1): Int {
        var itemDto = items.find { itemDto -> itemDto.item == item }
        // If there is already an item and it's stackable, return false
        if (itemDto != null && !itemDto.item.stackable) return -1
        // If there is no item, create a DTO for it
        if (itemDto == null) {
            val emptyPosition = findEmptyPosition()
            if (emptyPosition == -1) return -1

            itemDto = ItemDto(item, 0, emptyPosition, getId())
            items.add(itemDto)
        }
        itemDto.quantity += quantity
        itemDto.inventoryId = getId()
        return itemDto.position
    }

    fun removeItem(position: Int, quantity: Int = 1): Boolean {
        val itemDto = items.find { itemDto -> itemDto.position == position } ?: return false

        itemDto.quantity -= quantity
        // If there are no items left, remove it from the inventory
        if (itemDto.quantity <= 0) {
            items.remove(itemDto)
        }
        return true
    }

    fun moveItem(position: Int, endPosition: Int): Boolean {
        val itemDto = items.find { itemDto -> itemDto.position == position } ?: return false
        val newPosDto = items.find { newDto -> newDto.position == endPosition }
        if (newPosDto != null) return false
        itemDto.position = endPosition
        return true
    }

    fun addItemToPosition(
        item: Item,
        quantity: Int = 1,
        position: Int = findEmptyPosition(),
        replace: Boolean = true
    ): Boolean {
        val foundInPosition =
            items.find { itemDto -> itemDto.item == item && itemDto.position == position }
        if (foundInPosition != null && !replace) return false
        val foundItem = items.find { itemDto -> itemDto.item == item }
        if (foundItem != null && replace) {
            foundItem.quantity += quantity
        } else {
            val itemDto = ItemDto(item, quantity, position, getId())
            items.add(itemDto)
        }
        return true
    }

    fun addItemsToPositions(pairs: List<Pair<Item, Int>>) {
        for (pair in pairs) {
            val (item, position) = pair
            addItemToPosition(item, 1, position, false)
        }
    }

    fun moveItemFromInventory(
        inventory: Inventory,
        position: Int,
        quantity: Int = 1,
        endPosition: Int = findEmptyPosition(),
        replace: Boolean = true
    ): Boolean {
        val itemDto = inventory.items.find { itemDto -> itemDto.position == position } ?: return false
        inventory.removeItem(position, quantity)

        if (endPosition == -1) {
            this.addItem(itemDto.item, quantity)
        } else {
            this.addItemToPosition(itemDto.item, quantity, endPosition, replace)
        }
        return true
    }

    fun moveItemToInventory(
        inventory: Inventory,
        position: Int,
        quantity: Int = 1,
        endPosition: Int = -1,
        replace: Boolean = true
    ): Boolean {
        val itemDto = items.find { itemDto -> itemDto.position == position } ?: return false
        this.removeItem(position, quantity)
        if (endPosition == -1) {
            inventory.addItem(itemDto.item, quantity)
        } else {
            inventory.addItemToPosition(itemDto.item, quantity, endPosition, replace)
        }
        return true
    }

    fun moveAllItemsToInventory(inventory: Inventory): Boolean {
        for (item in items) {
            inventory.addItem(item.item, item.quantity)
        }
        items.clear()
        return true
    }
}