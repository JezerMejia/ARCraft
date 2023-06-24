package com.jezerm.pokepc.utils

import android.content.Context
import android.graphics.BitmapFactory
import android.util.Log
import androidx.lifecycle.LifecycleCoroutineScope
import com.gorisse.thomas.lifecycle.lifecycleScope
import io.github.sceneview.ar.ArSceneLifecycle
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


fun CreateNode(entityName: String, context: Context, lifecycleScope: LifecycleCoroutineScope): AugmentedImageNode {
    val node = AugmentedImageNode(
        entityName,
        bitmap = context.assets.open("augmentedimages/${entityName}.jpg").use(BitmapFactory::decodeStream),
        widthInMeters = 0.15f,
        onError = {
            Log.e("SceneView", "${entityName} image failed")
        },
        onUpdate = {node, augmentedImage ->
        },
    ).apply {

        lifecycleScope.launchWhenCreated {
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
        }
    }

    return node
}


