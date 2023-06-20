package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto

class CraftingTable : Inventory(9) {
    fun addItemToPosition(item: Item, position: Int): Boolean {
        var itemDto = items.find { itemDto -> itemDto.item == item && itemDto.position == position }
        if (itemDto != null) return false

        itemDto = ItemDto(item, 1, position)
        items.add(itemDto)
        return true
    }

    fun addItemsToPositions(pairs: List<Pair<Item, Int>>) {
        for (pair in pairs) {
            val (item, position) = pair
            addItemToPosition(item, position)
        }
    }

    fun moveItemFromInventory(inventory: Inventory, item: Item, position: Int): Boolean {
        if (!inventory.hasItem(item)) return false
        inventory.removeItem(item)
        this.addItemToPosition(item, position)
        return true
    }

    fun moveItemToInventory(inventory: Inventory, item: Item): Boolean {
        if (!this.hasItem(item)) return false
        this.removeItem(item)
        inventory.addItem(item)
        return true
    }

    fun moveAllItemsToInventory(inventory: Inventory): Boolean {
        for (item in items) {
            this.removeItem(item.item)
            inventory.addItem(item.item)
        }
        return true
    }

    fun canCraftRecipe(recipe: Recipe): Boolean {
        if (items.size != recipe.items.size) return false

        // Get the first position in the crafting table
        val firstItemsPosition = items.sortedBy { v -> v.position }.first().position

        for ((item, position) in recipe.items) {
            val pos = if (!recipe.anyPosition) position else firstItemsPosition + position - 1
            val itemInPosition = items.find { v -> v.position == pos } ?: return false

            if (itemInPosition.item != item)
                return false
        }
        return true
    }

    fun craftRecipe(): Item? {
        var finalRecipe: Recipe? = null
        for (recipe in Recipe.values()) {
            if (canCraftRecipe(recipe)) {
                finalRecipe = recipe
                break
            }
        }
        return finalRecipe?.result
    }
}