package com.jezerm.pokepc.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import com.google.ar.core.Anchor
import io.github.sceneview.SceneView
import io.github.sceneview.ar.arcore.isTracking
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import io.github.sceneview.model.model


fun CreateNode(entityName: String, context: Context): AugmentedImageNode {
    val node = AugmentedImageNode(
        entityName,
        bitmap = context.assets.open("augmentedimages/${entityName}.jpg").use(BitmapFactory::decodeStream),
        widthInMeters = 0.17f,
        onError = {
            Log.e("SceneView", "${entityName} image failed")
        },
        onUpdate = {node, augmentedImage ->
        },
    ).apply {
        val modelNode = ArModelNode(
            placementMode = PlacementMode.PLANE_VERTICAL,
            instantAnchor = true,
        )
        loadModelGlbAsync(
            glbFileLocation = "models/${entityName}.glb",
            scaleToUnits = 0.25f,
            centerOrigin = Position(0f, 0f, 0f),
            onLoaded = {
                Log.d("SceneView", "${entityName} model loaded")
            },
            onError = {
                Log.e("SceneView", "${entityName} model failed")
            }
        )
        addChild(modelNode)
    }

    return node
}


