package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto
import org.junit.Assert.*
import org.junit.Test

class InventoryTest {
    private val inventory = Inventory()

    @Test
    fun addItem() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.WOOD, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        assertArrayEquals(arrayOf(
            ItemDto(Item.GLASS, 1, 1),
            ItemDto(Item.WOOD, 4, 2),
            ItemDto(Item.OBSIDIAN, 2, 3),
        ), inventory.items.toArray())
    }

    @Test
    fun removeItem() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.WOOD, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        inventory.removeItem(Item.WOOD, 4)
        inventory.removeItem(Item.OBSIDIAN)

        assertArrayEquals(arrayOf(
            ItemDto(Item.GLASS, 1, 1),
            ItemDto(Item.OBSIDIAN, 1, 3),
        ), inventory.items.toArray())
    }

    @Test
    fun findEmptyPosition() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.WOOD, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        assertEquals(4, inventory.findEmptyPosition())

        inventory.removeItem(Item.WOOD, 4)
        assertEquals(2, inventory.findEmptyPosition())

        inventory.removeItem(Item.GLASS)
        assertEquals(1, inventory.findEmptyPosition())

        inventory.addItem(Item.GLASS)
        assertEquals(2, inventory.findEmptyPosition())
    }
}