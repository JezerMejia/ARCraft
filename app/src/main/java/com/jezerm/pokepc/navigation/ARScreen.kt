package com.jezerm.pokepc.navigation
import android.graphics.Bitmap
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
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.currentCompositionLocalContext
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.InventoryDialog
import io.github.sceneview.ar.ARScene
import io.github.sceneview.ar.node.ArModelNode
import io.github.sceneview.ar.node.ArNode
import io.github.sceneview.ar.node.AugmentedImageNode
import io.github.sceneview.ar.node.PlacementMode
import io.github.sceneview.math.Position
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.google.ar.core.AugmentedImage
import com.google.ar.core.AugmentedImageDatabase
import com.google.ar.core.Config
import com.google.ar.core.TrackingState
import com.jezerm.pokepc.R
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.theme.PixelBorderShape
import com.jezerm.pokepc.utils.CreateNode
import io.github.sceneview.ar.arcore.isTracking
import io.github.sceneview.node.Node
import kotlin.math.roundToInt

@Preview
@Composable
fun ARScreen() {
    val context = LocalContext.current
    val nodes = remember { mutableStateListOf<Node>() }

    val showInventoryDialog = remember { mutableStateOf(false) }

    if (showInventoryDialog.value)
          InventoryDialog(setShowDialog = {
              showInventoryDialog.value = it
          })

    val chestNode = CreateNode("chest", context)
    val enderChestNode = CreateNode("ender_chest", context)
    val craftingTableNode = CreateNode("crafting_table", context)
    val furnaceNode = CreateNode("furnace", context)
    val cowNode = CreateNode("cow", context)
    val chickenNode = CreateNode("chicken", context)
    val witherNode = CreateNode("wither", context)
    val beaconNode = CreateNode("beacon", context)
    val earthNode = CreateNode("earth", context)

    craftingTableNode.children[0].onTap = { motionEvent, renderable ->
        Toast.makeText(
            context, "${nodes.size}", Toast.LENGTH_LONG)   // Toast Notification
            .show();
    }
    nodes.add(craftingTableNode)
    nodes.add(chestNode)
    nodes.add(enderChestNode)
    nodes.add(furnaceNode)
    nodes.add(cowNode)
    nodes.add(chickenNode)
    nodes.add(witherNode)
    nodes.add(beaconNode)
    nodes.add(earthNode)

    val constraints = ConstraintSet {
        val inventoryBox = createRefFor("inventoryBox")
        val inventoryToggleBox = createRefFor("inventoryToggleBox")
        val timerBox = createRefFor("timerBox")

        constrain(inventoryBox) {
            bottom.linkTo(parent.bottom, margin = 5.dp)
            start.linkTo(parent.start, margin = 5.dp)
            width = Dimension.wrapContent
            height = Dimension.value(80.dp)
        }
        constrain(inventoryToggleBox) {
            bottom.linkTo(parent.bottom, margin = 5.dp)
            start.linkTo(inventoryBox.end)
            end.linkTo(parent.end)
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
        constrain(timerBox) {
            top.linkTo(parent.top, margin = 5.dp)
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

                arSceneView.configureSession { session, config ->
                    val database = AugmentedImageDatabase(session)
                    database.addImage("crafting_table", craftingTableNode.bitmap, 0.15f)
                    database.addImage("chest", chestNode.bitmap, 0.15f)
                    database.addImage("ender_chest", enderChestNode.bitmap, 0.15f)
                    database.addImage("furnace", furnaceNode.bitmap, 0.15f)
                    database.addImage("cow", cowNode.bitmap, 0.15f)
                    database.addImage("chicken", chickenNode.bitmap, 0.15f)
                    database.addImage("wither", witherNode.bitmap, 0.15f)
                    database.addImage("beacon", beaconNode.bitmap, 0.15f)
                    database.addImage("earth", earthNode.bitmap, 0.15f)
                    config.setAugmentedImageDatabase(database)
                }
//                arSceneView.onAugmentedImageUpdate += { augmentedImage ->
//                    Log.d("SceneView", augmentedImage.name.toString())
//                }
//                arSceneView.addChild(craftingTableNode)
//                arSceneView.addChild(chestNode)
//                arSceneView.addChild(furnaceNode)
//                arSceneView.addChild(craftingTableNode)
            },
            onSessionCreate = { session ->
                // Configure the ARCore session
            },
            onFrame = { arFrame ->
                // Retrieve ARCore frame update
            },
            onTap = { hitResult ->
                // User tapped in the AR view
                this.configureSession { arSession, config ->
                    Log.d("SceneView", config.augmentedImageDatabase.numImages.toString())
                }
            },
            onTrackingFailureChanged = {
                Log.e("SceneView", it.toString())
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
                    .layoutId("inventoryToggleBox")
            ) {
                //Placeholder por ahora
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Black)
                        .border(5.dp, Color.Gray)
                        .clickable {
                            // onClick
                            showInventoryDialog.value = true
                        }
                )
            }

            Box(
                modifier = Modifier
                    .layoutId("timerBox")
                    .background(Color.Black)
                    .border(5.dp, Color.Gray)
            )
        }
    }
}