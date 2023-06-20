package com.jezerm.pokepc.entities

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotEquals
import org.junit.Test

class CraftingTableTest {
    private val craftingTable = CraftingTable()

    @Test
    fun canCraftCakeRecipe() {
        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.MILK_BUCKET to 1,
                Item.MILK_BUCKET to 2,
                Item.MILK_BUCKET to 3,
                Item.SUGAR to 4,
                Item.EGG to 5,
                Item.SUGAR to 6,
                Item.WHEAT to 7,
                Item.WHEAT to 8,
                Item.WHEAT to 9,
            )
        )

        val item = craftingTable.craftRecipe()
        assertEquals(Item.CAKE, item)
    }

    @Test
    fun canCraftBeaconRecipe() {
        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.GLASS to 1,
                Item.GLASS to 2,
                Item.GLASS to 3,
                Item.GLASS to 4,
                Item.NETHER_STAR to 5,
                Item.GLASS to 6,
                Item.OBSIDIAN to 7,
                Item.OBSIDIAN to 8,
                Item.OBSIDIAN to 9,
            )
        )

        val item = craftingTable.craftRecipe()
        assertEquals(Item.BEACON, item)
    }

    @Test
    fun canCraftStickRecipe() {
        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.WOOD to 1,
                Item.WOOD to 4,
            )
        )
        assertEquals(Item.STICK, craftingTable.craftRecipe())

        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.WOOD to 2,
                Item.WOOD to 5,
            )
        )
        assertEquals(Item.STICK, craftingTable.craftRecipe())

        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.WOOD to 6,
                Item.WOOD to 9,
            )
        )
        assertEquals(Item.STICK, craftingTable.craftRecipe())

        craftingTable.items.clear()
        craftingTable.addItemsToPositions(
            listOf(
                Item.WOOD to 7,
                Item.WOOD to 9,
            )
        )
        assertNotEquals(Item.STICK, craftingTable.craftRecipe())
    }
}