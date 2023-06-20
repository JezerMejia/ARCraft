package com.jezerm.pokepc.navigation

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.jezerm.pokepc.R
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.dialog.InventoryDialog
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.theme.PixelBorderShape
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

    val showInventoryDialog = remember { mutableStateOf(false) }
    val showCraftingDialog = remember { mutableStateOf(false) }
    val showSmeltingDialog = remember { mutableStateOf(false) }

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
                    TextShadow("Tiempo Restante", MaterialTheme.typography.h3, TextAlign.Center)
                    Spacer(modifier = Modifier.height(8.dp))
                    TextShadow("XX:XX", MaterialTheme.typography.h3, TextAlign.Center)
                }
            }
        }
    }
}
