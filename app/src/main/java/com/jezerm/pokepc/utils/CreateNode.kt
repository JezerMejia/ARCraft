package com.jezerm.pokepc.utils

import android.content.Context
import android.graphics.BitmapFactory
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

fun CreateNode(entityName: String, context: Context): AugmentedImageNode {
    val node = AugmentedImageNode(
        entityName,
        bitmap = context.assets.open("augmentedimages/${entityName}.jpg").use(BitmapFactory::decodeStream),
    ).apply {
        val modelNode = ArModelNode(
            placementMode = PlacementMode.BEST_AVAILABLE,
            instantAnchor = false,
        )
        modelNode.loadModelGlbAsync(
            glbFileLocation = "models/${entityName}.glb",
            scaleToUnits = 1f,
            centerOrigin = Position(0f, 0f, 0f),
        )
        addChild(modelNode)
    }
    return node
}