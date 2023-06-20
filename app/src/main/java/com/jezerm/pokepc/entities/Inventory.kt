package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.data.RoomRepository
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*

open class Inventory(val size: Int = 20) {
    val items: ArrayList<ItemDto> = ArrayList()

    suspend fun initFromDatabase() {
        val repository = RoomRepository.getInstance()
        val newItems = repository.itemsDto.firstOrNull() ?: listOf()
        items.clear()
        items.addAll(newItems)
    }

    suspend fun saveToDatabase() {
        val repository = RoomRepository.getInstance()
        val oldItems = repository.itemsDto.firstOrNull() ?: listOf()

        for (item in items) {
            if (oldItems.contains(item))
                repository.update(item)
            else
                repository.insert(item)
        }
        for (old in oldItems) {
            if (!items.contains(old))
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

            itemDto = ItemDto(item, 0, emptyPosition)
            items.add(itemDto)
        }
        itemDto.quantity += quantity
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
}