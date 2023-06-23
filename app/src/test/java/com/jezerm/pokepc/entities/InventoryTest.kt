package com.jezerm.pokepc.entities

import com.jezerm.pokepc.data.ItemDto
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertEquals
import org.junit.Test

class InventoryTest {
    private val inventory = Inventory()

    @Test
    fun addItems() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.OAK_PLANKS, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        assertArrayEquals(
            arrayOf(
                ItemDto(Item.GLASS, 1, 1, inventory.getId()),
                ItemDto(Item.OAK_PLANKS, 4, 2, inventory.getId()),
                ItemDto(Item.OBSIDIAN, 2, 3, inventory.getId()),
            ), inventory.items.toArray()
        )
    }

    @Test
    fun addSameItem() {
        inventory.addItem(Item.GLASS, 2)

        assertArrayEquals(
            arrayOf(
                ItemDto(Item.GLASS, 2, 1, inventory.getId()),
            ), inventory.items.toArray()
        )

        inventory.addItem(Item.GLASS, 4)
        assertArrayEquals(
            arrayOf(
                ItemDto(Item.GLASS, 6, 1, inventory.getId()),
            ), inventory.items.toArray()
        )
    }

    @Test
    fun removeItem() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.OAK_PLANKS, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        inventory.removeItem(Item.OAK_PLANKS, 4)
        inventory.removeItem(Item.OBSIDIAN)

        assertArrayEquals(
            arrayOf(
                ItemDto(Item.GLASS, 1, 1, inventory.getId()),
                ItemDto(Item.OBSIDIAN, 1, 3, inventory.getId()),
            ), inventory.items.toArray()
        )
    }

    @Test
    fun findEmptyPosition() {
        inventory.addItem(Item.GLASS)
        inventory.addItem(Item.OAK_PLANKS, 4)
        inventory.addItem(Item.OBSIDIAN, 2)

        assertEquals(4, inventory.findEmptyPosition())

        inventory.removeItem(Item.OAK_PLANKS, 4)
        assertEquals(2, inventory.findEmptyPosition())

        inventory.removeItem(Item.GLASS)
        assertEquals(1, inventory.findEmptyPosition())

        inventory.addItem(Item.GLASS)
        assertEquals(2, inventory.findEmptyPosition())
    }
}