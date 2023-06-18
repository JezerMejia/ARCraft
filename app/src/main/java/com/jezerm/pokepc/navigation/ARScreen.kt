package com.jezerm.pokepc.navigation

import android.graphics.BitmapFactory
import android.util.Log
import android.view.MotionEvent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.jezerm.pokepc.utils.CreateNode
import kotlin.math.roundToInt

@Preview
@Composable
fun ARScreen() {
    val context = LocalContext.current
    val nodes = remember { mutableStateListOf<ArNode>() }

//    val chestNode = CreateNode("chest", context)
    val craftingTableNode = CreateNode("crafting_table", context)
    val furnaceNode = CreateNode("furnace", context)
    val cowNode = CreateNode("cow", context)
    val chickenNode = CreateNode("chicken", context)
    val whitherNode = CreateNode("whither", context)
    val beaconNode = CreateNode("whither", context)


//    nodes.add(chestNode)
    nodes.add(craftingTableNode)
    nodes.add(furnaceNode)
    nodes.add(cowNode)
    nodes.add(chickenNode)
    nodes.add(whitherNode)
    nodes.add(beaconNode)

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