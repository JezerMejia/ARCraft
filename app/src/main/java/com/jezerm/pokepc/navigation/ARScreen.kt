package com.jezerm.pokepc.navigation

import android.graphics.BitmapFactory
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
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

    val constraints = ConstraintSet {
        val inventoryBox = createRefFor("inventoryBox")
        val timerBox = createRefFor("timerBox")

        constrain(inventoryBox) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.matchParent
            height = Dimension.value(80.dp)
        }
        constrain(timerBox) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            width = Dimension.value(200.dp)
            height = Dimension.value(80.dp)
        }
    }

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
        ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .layoutId("inventoryBox")
            ) {
                Row {
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = Color.Black)
                            .border(5.dp, Color.Gray)
                    )
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = Color.Black)
                            .border(5.dp, Color.Gray)
                    )
                    Box(
                        modifier = Modifier
                            .size(80.dp)
                            .background(color = Color.Black)
                            .border(5.dp, Color.Gray)
                    )
                }
            }

            Box(
                modifier = Modifier
                    .background(Color.Gray)
                    .layoutId("timerBox")
                    .background(color = Color.Black)
                    .border(5.dp, Color.Gray)
            )
        }
    }
}
