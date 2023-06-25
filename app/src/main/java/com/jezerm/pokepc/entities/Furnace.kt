package com.jezerm.pokepc.entities

class Furnace: Inventory(1, InventoryType.NO_SAVE) {
    fun canBurnRecipe(recipe: BurnRecipe): Boolean {
        val itemDto = items.firstOrNull() ?: return false
        if (itemDto.item != recipe.item) return false
        return true
    }
    fun craftRecipe(): Item? {
        var finalRecipe: BurnRecipe? = null
        for (recipe in BurnRecipe.values()) {
            if (canBurnRecipe(recipe)) {
                finalRecipe = recipe
                break
            }
        }
        return finalRecipe?.result
    }
}