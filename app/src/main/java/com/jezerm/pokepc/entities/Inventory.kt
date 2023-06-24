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

    fun addItem(item: Item, quantity: Int = 1): Boolean {
        var itemDto = items.find { itemDto -> itemDto.item == item }
        // If there is already an item and it's stackable, return false
        if (itemDto != null && !itemDto.item.stackable) return false
        // If there is no item, create a DTO for it
        if (itemDto == null) {
            val emptyPosition = findEmptyPosition()
            if (emptyPosition == -1) return false

            itemDto = ItemDto(item, 0, emptyPosition, getId())
            items.add(itemDto)
        }
        itemDto.quantity += quantity
        itemDto.inventoryId = getId()
        return true
    }

    fun removeItem(item: Item, quantity: Int = 1): Boolean {
        val itemDto = items.find { itemDto -> itemDto.item == item } ?: return false

        itemDto.quantity -= quantity
        // If there are no items left, remove it from the inventory
        if (itemDto.quantity <= 0) {
            items.remove(itemDto)
        }
        return true
    }

    fun moveItem(item: Item, position: Int): Boolean {
        val itemDto = items.find { itemDto -> itemDto.item == item } ?: return false
        val newPosDto = items.find { newDto -> newDto.position == position }
        if (newPosDto != null) return false
        itemDto.position = position
        return true
    }

    fun addItemToPosition(
        item: Item,
        quantity: Int = 1,
        position: Int = findEmptyPosition()
    ): Boolean {
        var itemDto = items.find { itemDto -> itemDto.item == item && itemDto.position == position }
        if (itemDto != null) return false

        itemDto = ItemDto(item, quantity, position, getId())
        items.add(itemDto)
        return true
    }

    fun addItemsToPositions(pairs: List<Pair<Item, Int>>) {
        for (pair in pairs) {
            val (item, position) = pair
            addItemToPosition(item, 1, position)
        }
    }

    fun moveItemFromInventory(
        inventory: Inventory,
        item: Item,
        quantity: Int = 1,
        position: Int = findEmptyPosition()
    ): Boolean {
        if (!inventory.hasItem(item)) return false
        if (position == -1) return false
        inventory.removeItem(item)
        return this.addItemToPosition(item, quantity, position)
    }

    fun moveItemToInventory(inventory: Inventory, item: Item, quantity: Int = 1): Boolean {
        if (!this.hasItem(item)) return false
        this.removeItem(item, quantity)
        inventory.addItem(item, quantity)
        return true
    }

    fun moveItemToInventory(
        inventory: Inventory,
        item: Item,
        quantity: Int = 1,
        position: Int = -1
    ): Boolean {
        if (!this.hasItem(item)) return false
        this.removeItem(item, quantity)
        if (position == -1) {
            inventory.addItem(item, quantity)
        } else {
            inventory.addItemToPosition(item, quantity, position)
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