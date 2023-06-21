package com.jezerm.pokepc.entities


class Chest(val chestType: ChestType): Inventory(size = 8, InventoryType.CHEST) {
    override fun getId(): Int = chestType.value

    enum class ChestType(val value: Int) {
        ONE(11),
        TWO(12),
        THREE(13),
    }
}