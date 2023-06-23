package com.jezerm.pokepc.data

import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.entities.Item

val chestOnePopulate = arrayOf(
    ItemDto(Item.WHEAT, 3, 1, Chest.ChestType.ONE.value),
    ItemDto(Item.SUGAR_CANE, 2, 3, Chest.ChestType.ONE.value),
    ItemDto(Item.IRON, 8, 5, Chest.ChestType.ONE.value),
)
val chestTwoPopulate = arrayOf(
    ItemDto(Item.SAND, 5, 2, Chest.ChestType.TWO.value),
    ItemDto(Item.OBSIDIAN, 4, 4, Chest.ChestType.TWO.value),
    ItemDto(Item.DIAMOND, 1, 5, Chest.ChestType.TWO.value),
)
val chestThreePopulate = arrayOf(
    ItemDto(Item.OAK_PLANKS, 8, 1, Chest.ChestType.THREE.value),
    ItemDto(Item.DIAMOND, 1, 6, Chest.ChestType.THREE.value),
)
