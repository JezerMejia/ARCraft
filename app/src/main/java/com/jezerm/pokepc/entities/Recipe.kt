package com.jezerm.pokepc.entities

enum class Recipe(val items: Array<Pair<Item, Int>>, val result: Item, val anyPosition: Boolean = false) {
    // Basic recipes
    STICK(
        arrayOf(
            Item.OAK_PLANKS to 2,
            Item.OAK_PLANKS to 5,
        ),
        Item.STICK,
        true
    ),
    SUGAR(
        arrayOf(
            Item.SUGAR_CANE to 1,
        ),
        Item.SUGAR,
        true
    ),
    BUCKET(
        arrayOf(
            Item.IRON to 1,
            Item.IRON to 3,
            Item.IRON to 5,
        ),
        Item.BUCKET,
        true
    ),
    IRON_PICKAXE(
        arrayOf(
            Item.IRON to 1,
            Item.IRON to 2,
            Item.IRON to 3,
            Item.STICK to 5,
            Item.STICK to 8,
        ),
        Item.IRON_PICKAXE
    ),
    DIAMOND_SWORD(
        arrayOf(
            Item.DIAMOND to 2,
            Item.DIAMOND to 5,
            Item.STICK to 8,
        ),
        Item.DIAMOND_SWORD
    ),

    // End recipes
    CAKE(
        arrayOf(
            Item.MILK_BUCKET to 1,
            Item.MILK_BUCKET to 2,
            Item.MILK_BUCKET to 3,
            Item.SUGAR to 4,
            Item.EGG to 5,
            Item.SUGAR to 6,
            Item.WHEAT to 7,
            Item.WHEAT to 8,
            Item.WHEAT to 9,
        ),
        Item.CAKE
    ),
    BEACON(
        arrayOf(
            Item.GLASS to 1,
            Item.GLASS to 2,
            Item.GLASS to 3,
            Item.GLASS to 4,
            Item.NETHER_STAR to 5,
            Item.GLASS to 6,
            Item.OBSIDIAN to 7,
            Item.OBSIDIAN to 8,
            Item.OBSIDIAN to 9,
        ),
        Item.BEACON
    )
}