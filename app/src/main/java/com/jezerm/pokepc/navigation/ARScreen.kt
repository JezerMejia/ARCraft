package com.jezerm.pokepc.navigation

import androidx.compose.foundation.Image
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.dialog.InventoryDialog
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.ui.components.TextShadow
import io.github.sceneview.ar.ARScene
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import com.google.ar.core.AugmentedImageDatabase
import com.jezerm.pokepc.R
import com.jezerm.pokepc.ui.theme.PixelBorderShape
import com.jezerm.pokepc.utils.CreateNode
import io.github.sceneview.node.Node

@Preview
@Composable
fun ARScreen() {
    val context = LocalContext.current
    val nodes = remember { mutableStateListOf<Node>() }

    val showInventoryDialog = remember { mutableStateOf(false) }
    val showCraftingDialog = remember { mutableStateOf(false) }
    val showSmeltingDialog = remember { mutableStateOf(false) }
    val showChestDialog = remember { mutableStateOf(false) }

    if (showInventoryDialog.value)
          InventoryDialog(setShowDialog = {
              showInventoryDialog.value = it
          })

    if (showCraftingDialog.value)
        CraftingTableDialog(setShowDialog = {
            showCraftingDialog.value = it
        })

    if (showSmeltingDialog.value)
        FurnaceDialog(setShowDialog = {
            showSmeltingDialog.value = it
        })

    if (showChestDialog.value)
        ChestInventoryDialog(setShowDialog = {
            showSmeltingDialog.value = it
        }, "", Inventory())

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
            height = Dimension.wrapContent
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
            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }

    val hotbarSlotModifier = Modifier
        .size(60.dp)
        .background(color = Color(143, 143, 143))
        .border(2.dp, Color(85, 85, 85))

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
            Surface(
                modifier = Modifier
                    .layoutId("inventoryBox"),
                shape = PixelBorderShape()
            ) {
                Row(
                    modifier = Modifier
                        .background(Color(143, 143, 143))
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(
                        modifier = hotbarSlotModifier
                            .clickable {
                                showCraftingDialog.value = true
                            }
                    )
                    Box(
                        modifier = hotbarSlotModifier
                            .clickable {
                                showSmeltingDialog.value = true
                            }
                    )
                    Box(
                        modifier = hotbarSlotModifier
                            .clickable {
                                showChestDialog.value = true
                            }
                    )
                    Box(
                        modifier = hotbarSlotModifier
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .layoutId("inventoryToggleBox"),
                shape = PixelBorderShape()
            ) {
                //Placeholder por ahora
                Box(
                    modifier = Modifier
                        .size(80.dp)
                        .background(Color.Gray)
                        .clickable {
                            // onClick
                            showInventoryDialog.value = true
                        }
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.inventory_icon),
                        contentDescription = "",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(70.dp)
                            .align(Alignment.Center)
                    )
                }
            }

            Surface(
                modifier = Modifier
                    .layoutId("timerBox"),
                shape = PixelBorderShape()
            ) {
                Column(
                    modifier = Modifier
                        .background(Color(143, 143, 143))
                        .padding(24.dp, 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    TextShadow("Tiempo Restante", Modifier, MaterialTheme.typography.h3, TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextShadow("XX:XX", Modifier, MaterialTheme.typography.h3, TextAlign.Center)
                }
            }
        }
    }
}
