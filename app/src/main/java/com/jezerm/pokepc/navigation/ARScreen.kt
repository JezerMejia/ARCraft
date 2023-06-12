package com.jezerm.pokepc.navigation

import android.graphics.BitmapFactory
import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position

@Composable
fun ARScreen() {
    val context = LocalContext.current
    val nodes = remember { mutableStateListOf<ArNode>() }

    val earthNode = AugmentedImageNode(
        "earth",
        bitmap = context.assets.open("augmentedimages/earth.jpeg").use(BitmapFactory::decodeStream),
    ).apply {
        val modelNode = ArModelNode(
            placementMode = PlacementMode.BEST_AVAILABLE,
            instantAnchor = false,
        )
        modelNode.loadModelGlbAsync(
            glbFileLocation = "models/earth.glb",
            scaleToUnits = 1f,
            centerOrigin = Position(0f, 0f, 0f),
        )
        addChild(modelNode)
    }
    nodes.add(earthNode)

    Box(modifier = Modifier.fillMaxSize()) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            nodes = nodes,
            planeRenderer = true,
            onCreate = { arSceneView ->
                // Apply your configuration
            },
            onSessionCreate = { session ->
                // Configure the ARCore session
            },
            onFrame = { arFrame ->
                // Retrieve ARCore frame update
            },
            onTap = { hitResult ->
                // User tapped in the AR view
            }
        )
    }
}