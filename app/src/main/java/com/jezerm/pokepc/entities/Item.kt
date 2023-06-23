package com.jezerm.pokepc.entities

import com.jezerm.pokepc.R

enum class Item(val value: String, val image: Int = 0, val stackable: Boolean = true) {
    // Basic items
    AIR("", R.drawable.air, false),
    SAND("Arena", R.drawable.sand),
    OAK_LOG("Tronco de madera", R.drawable.oak_log),
    OAK_PLANKS("Tablones de madera", R.drawable.oak_planks),
    STICK("Palo", R.drawable.stick),
    IRON("Hierro", R.drawable.iron_ingot),
    DIAMOND("Diamante", R.drawable.diamond),
    SUGAR_CANE("Caña de azúcar", R.drawable.sugar_cane),

    // Cake ingredients
    MILK_BUCKET("Cubo con leche", R.drawable.milk_bucket),
    SUGAR("Azúcar", R.drawable.sugar),
    EGG("Huevo", R.drawable.egg),
    WHEAT("Trigo", R.drawable.wheat),

    // Beacon ingredients
    GLASS("Vidrio", R.drawable.glass),
    NETHER_STAR("Estrella del Nether", R.drawable.nether_star, false),
    OBSIDIAN("Obsidiana", R.drawable.obsidian),

    // Tools or actionable items
    BUCKET("Cubo", R.drawable.bucket),
    IRON_PICKAXE("Pico de hierro", R.drawable.iron_pickaxe, false),
    DIAMOND_SWORD("Espada de diamante", R.drawable.diamond_sword, false),

    // End items
    CAKE("Pastel", R.drawable.cake),
    BEACON("Beacon", R.drawable.beacon),
}