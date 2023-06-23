package com.jezerm.pokepc.navigation

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.google.ar.core.AugmentedImageDatabase
import com.jezerm.pokepc.R
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.dialog.InventoryDialog
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import com.jezerm.pokepc.utils.CreateNode
import io.github.sceneview.ar.ARScene

@Preview
@Composable
fun ARScreen() {
    val context = LocalContext.current

    val grayColor = Color(198, 198, 198)

    val showInventoryDialog = remember { mutableStateOf(false) }
    val showCraftingDialog = remember { mutableStateOf(false) }
    val showSmeltingDialog = remember { mutableStateOf(false) }

    val showChestDialog = remember { mutableStateOf(false) }
    val lastChestOpened = remember { mutableStateOf(Chest.ChestType.ONE) }

    var currentHotbar = ArrayList<Pair<Item, Int>>()
    val latestSelectedItem = remember { mutableStateOf(-1) }

    if (showInventoryDialog.value)
        InventoryDialog(
            setShowDialog = {
                showInventoryDialog.value = it
            }
        ) {
            currentHotbar = it
        }

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
            showChestDialog.value = it
        }, lastChestOpened.value)

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

    Box(modifier = Modifier.fillMaxSize()) {
        ARScene(
            modifier = Modifier.fillMaxSize(),
            planeRenderer = true,
            onCreate = { arSceneView ->
                val chestNode = CreateNode("chest", context)
                val enderChestNode = CreateNode("ender_chest", context)
                val xmasChestNode = CreateNode("xmas_chest", context)
                val craftingTableNode = CreateNode("crafting_table", context)
                val furnaceNode = CreateNode("furnace", context)
                val cowNode = CreateNode("cow", context)
                val chickenNode = CreateNode("chicken", context)
                val witherNode = CreateNode("wither", context)
                val beaconNode = CreateNode("beacon", context)
                val earthNode = CreateNode("earth", context)

                craftingTableNode.onTap = { motionEvent, renderable ->
                    showCraftingDialog.value = true
                }
                chestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.ONE
                    showChestDialog.value = true
                }
                enderChestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.TWO
                    showChestDialog.value = true
                }
                xmasChestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.THREE
                    showChestDialog.value = true
                }
                furnaceNode.onTap = { motionEvent, renderable ->
                    showSmeltingDialog.value = true
                }

                arSceneView.addChild(craftingTableNode)
                arSceneView.addChild(chestNode)
                arSceneView.addChild(enderChestNode)
                arSceneView.addChild(xmasChestNode)
                arSceneView.addChild(furnaceNode)
                arSceneView.addChild(cowNode)
                arSceneView.addChild(chickenNode)
                arSceneView.addChild(witherNode)
                arSceneView.addChild(beaconNode)
                arSceneView.addChild(earthNode)

                arSceneView.configureSession { session, config ->
                    val database = AugmentedImageDatabase(session)
                    database.addImage("crafting_table", craftingTableNode.bitmap, 0.15f)
                    database.addImage("chest", chestNode.bitmap, 0.15f)
                    database.addImage("ender_chest", enderChestNode.bitmap, 0.15f)
                    database.addImage("xmas_chest", xmasChestNode.bitmap, 0.15f)
                    database.addImage("furnace", furnaceNode.bitmap, 0.15f)
                    database.addImage("cow", cowNode.bitmap, 0.15f)
                    database.addImage("chicken", chickenNode.bitmap, 0.15f)
                    database.addImage("wither", witherNode.bitmap, 0.15f)
                    database.addImage("beacon", beaconNode.bitmap, 0.15f)
                    database.addImage("earth", earthNode.bitmap, 0.15f)
                    config.augmentedImageDatabase = database
                }
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
        )
        ConstraintLayout(
            constraints, modifier = Modifier
                .fillMaxSize()
                .displayCutoutPadding()
        ) {

            val hotbarItems = ArrayList<Pair<Item, Int>>()

            for (i in 1..4) {
                val (item, pos) = currentHotbar.find { v -> v.second == i } ?: Pair(Item.AIR, i)
                hotbarItems.add(item to pos)
            }

            Surface(modifier = Modifier.layoutId("inventoryBox")) {
                Card(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                    shape = RectangleShape,
                    backgroundColor = grayColor
                ) {
                    Column(modifier = Modifier.padding(16.dp, 8.dp)) {
                        LazyVerticalGrid(
                            modifier = Modifier
                                .widthIn(130.dp, 260.dp)
                                .heightIn(80.dp, 76.dp),
                            columns = GridCells.Fixed(4),
                            horizontalArrangement = Arrangement.spacedBy(
                                6.dp,
                                Alignment.CenterHorizontally
                            ),
                            verticalArrangement = Arrangement.Center,
                            userScrollEnabled = false
                        ) {
                            items(hotbarItems, key = { c -> c.second }) { (item, position) ->
                                val imageBitmap = ImageBitmap.imageResource(item.image)
                                Surface(
                                    modifier = Modifier
                                        .clickable {
                                            latestSelectedItem.value = position
                                        },
                                    color = if (latestSelectedItem.value == position) Color(
                                        94,
                                        94,
                                        94
                                    )
                                    else Color(139, 139, 139)
                                ) {
                                    Image(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .wrapContentHeight()
                                            .clip(RectangleShape)
                                            .insetBorder(
                                                lightSize = 4.dp,
                                                darkSize = 4.dp,
                                                borderPadding = 0.dp
                                            )
                                            .padding(4.dp),
                                        bitmap = imageBitmap,
                                        filterQuality = FilterQuality.None,
                                        contentDescription = item.value,
                                        contentScale = ContentScale.FillWidth,
                                        alignment = Alignment.Center
                                    )
                                }
                            }
                        }
                    }
                }
            }

            Surface(modifier = Modifier.layoutId("inventoryToggleBox")) {
                Card(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                    shape = RectangleShape,
                    backgroundColor = grayColor
                ) {
                    Box(
                        modifier = Modifier
                            .size(92.dp)
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
                                .size(76.dp)
                                .align(Alignment.Center)
                        )
                    }
                }
            }

            Surface(modifier = Modifier.layoutId("timerBox")) {
                Card(
                    modifier = Modifier
                        .clip(RectangleShape)
                        .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                    shape = RectangleShape,
                    backgroundColor = grayColor
                ) {
                    Column(
                        modifier = Modifier
                            .background(Color(143, 143, 143))
                            .padding(24.dp, 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        TextShadow(
                            modifier = Modifier,
                            text = "Tiempo Restante",
                            MaterialTheme.typography.h3,
                            TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        TextShadow(
                            modifier = Modifier,
                            text = "XX:XX",
                            MaterialTheme.typography.h3,
                            TextAlign.Center
                        )
                    }
                }
            }
        }
    }
}
