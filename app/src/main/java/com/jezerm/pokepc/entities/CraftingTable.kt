package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto

class CraftingTable : Inventory(9, InventoryType.NO_SAVE) {

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