package com.jezerm.pokepc.entities

enum class ItemInfo(val item: Item, val description: String) {
    OAK_LOG(
        Item.OAK_LOG,
        "Los troncos de madera los consigues cortando árboles, o consiguiéndolos en un cofre."
    ),
    OAK_PLANKS(
        Item.OAK_PLANKS,
        "Útiles para crearte una casa y otros elementos básicos. Lo consigues al poner un tronco de madera en una mesa de crafteo."
    ),
    STICK(
        Item.STICK,
        "Es útil para craftear varias herramientas."
    ),
    BUCKET(
        Item.BUCKET,
        "Un cubo vacío. Es útil para contener fluidos como el agua, la leche o la lava."
    ),
    MILK_BUCKET(
        Item.MILK_BUCKET,
        "Puedes conseguir un cubo de leche al ordeñar una vaca con un cubo vacío."
    ),
    GOT_NEW_MILK_BUCKET(
        Item.MILK_BUCKET,
        "Ha conseguido un cubo con leche!"
    ),
    EGG(
        Item.EGG,
        "Las gallinas sueltan huevos. Busca una gallina y tócala para que te suelte uno."
    ),
    GOT_NEW_EGG(
        Item.EGG,
        "Ha conseguido un huevo. Con esto bastara para el pastel!"
    ),
    SUGAR_CANE(
        Item.SUGAR_CANE,
        "La caña de azúcar es útil para hacer papel y azúcar. Puede encontrarse cerca de lagos... o en algún cofre."
    ),
    SUGAR(
        Item.SUGAR,
        "El azúcar se consigue de muchas maneras, una de ellas es con caña de azúcar."
    ),
    WHEAT(
        Item.WHEAT,
        "El trigo se consigue plantando. No hay tiempo para eso, así que búscalo en algún cofre."
    ),
    GLASS(
        Item.GLASS,
        "El vidrio se consigue quemando arena a unos 1500°C. El horno de por acá llega a tales temperaturas."
    ),
    IRON(
        Item.IRON,
        "Funde un mineral de hierro a unos 1500°C en un horno para conseguir un lingote de hierro."
    ),
    OBSIDIAN(
        Item.OBSIDIAN,
        "Una roca difícil de minar que se obtiene al fundir lava con agua."
    ),
    NETHER_STAR(
        Item.NETHER_STAR,
        "Una estrella extraña que suelta el temible Wither al morir."
    ),
    GOT_NEW_NETHER_STAR(
        Item.NETHER_STAR,
        "Una estrella extraña que brilla ominosamente. Podria funcionar perfectamente para darle poder a un beacon!"
    ),
    DIAMOND(
        Item.DIAMOND,
        "Un mineral raro que se encuentra en las profundidades de la Tierra... o en cualquier cofre de por acá."
    ),
    DIAMOND_SWORD(
        Item.DIAMOND_SWORD,
        "Una espada muy poderosa capaz de matar a monstruos como el temible Wither."
    ),

    CAKE(
        Item.CAKE,
        "Un delicioso pastel."
    ),
    BEACON(
        Item.BEACON,
        "Un bloque especial que proyecta un haz de luz hacia el cielo."
    ),
    ;

    companion object {
        fun valueOf(item: Item): ItemInfo? = values().find { it.item == item }
    }
}