package com.jezerm.pokepc.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import io.github.sceneview.SceneView
import io.github.sceneview.ar.arcore.isTracking
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position



fun CreateNode(entityName: String, context: Context): AugmentedImageNode {
    val node = AugmentedImageNode(
        entityName,
        bitmap = context.assets.open("augmentedimages/${entityName}.jpg").use(BitmapFactory::decodeStream),
        widthInMeters = 0.17f,
        onError = {
            Log.e("SceneView", "${entityName} image failed")
        },
        onUpdate = {node, augmentedImage ->
            if(augmentedImage.isTracking) {
//                Log.d("SceneView", augmentedImage.name.toString()) for debug purposes
                val modelNode = node.children[0]
                if(modelNode is ArModelNode) {
                    if (modelNode?.anchor == null) {
                        modelNode?.anchor = augmentedImage.createAnchor(augmentedImage.centerPose)
                    }
                }
            }
        },
    ).apply {
        val modelNode = ArModelNode(
            placementMode = PlacementMode.PLANE_HORIZONTAL,
            instantAnchor = true,
        )
        modelNode.loadModelGlbAsync(
            glbFileLocation = "models/${entityName}.glb",
            scaleToUnits = 0.5f,
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


