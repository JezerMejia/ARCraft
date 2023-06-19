package com.jezerm.pokepc.entities

enum class Item(val value: String, val stackable: Boolean = true) {
    // Basic items
    SAND("Arena"),
    WOOD("Madera"),
    STICK("Palo"),
    IRON("Hierro"),
    DIAMOND("Diamante"),
    SUGAR_CANE("Caña de azúcar"),

    // Cake ingredients
    MILK_BUCKET("Cubo con leche"),
    SUGAR("Azúcar"),
    EGG("Huevo"),
    WHEAT("Trigo"),

    // Beacon ingredients
    GLASS("Vidrio"),
    NETHER_STAR("Estrella del Nether", false),
    OBSIDIAN("Obsidiana"),

    // Tools or actionable items
    BUCKET("Cubo"),
    IRON_PICKAXE("Pico de hierro", false),
    DIAMOND_SWORD("Espada de diamante", false),

    // End items
    CAKE("Pastel"),
    BEACON("Beacon"),
}