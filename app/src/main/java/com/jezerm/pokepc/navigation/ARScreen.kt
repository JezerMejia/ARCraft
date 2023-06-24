package com.jezerm.pokepc.navigation

import android.media.MediaPlayer
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.google.ar.core.AugmentedImageDatabase
import com.jezerm.pokepc.R
import com.google.ar.core.dependencies.i
import com.jezerm.pokepc.data.ItemDto
import com.jezerm.pokepc.dialog.ChestInventoryDialog
import com.jezerm.pokepc.dialog.CraftingTableDialog
import com.jezerm.pokepc.dialog.FurnaceDialog
import com.jezerm.pokepc.dialog.InventoryDialog
import io.github.sceneview.ar.ARScene
import com.google.ar.core.Config
import com.gorisse.thomas.lifecycle.lifecycleScope
import com.jezerm.pokepc.dialog.ItemInfoDialog
import com.jezerm.pokepc.entities.Chest
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.entities.ItemInfo
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.components.TimeBox
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import com.jezerm.pokepc.utils.CreateNode
import io.github.sceneview.ar.ARScene
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import com.jezerm.pokepc.R

@Preview
@Composable
fun ARScreen(navController: NavController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val grayColor = Color(198, 198, 198)

    val buttonClickedMP = MediaPlayer.create(context, R.raw.button_click)
    val newItemMP = MediaPlayer.create(context, R.raw.new_item)
    val newItemBigMP = MediaPlayer.create(context, R.raw.new_item_big)
    val openChestMP = MediaPlayer.create(context, R.raw.chest_open)

    val showInventoryDialog = remember { mutableStateOf(false) }
    val showCraftingDialog = remember { mutableStateOf(false) }
    val showSmeltingDialog = remember { mutableStateOf(false) }

    val showChestDialog = remember { mutableStateOf(false) }
    val lastChestOpened = remember { mutableStateOf(Chest.ChestType.ONE) }

    val showNewItemDialog = remember { mutableStateOf(false) }
    val lastNewItem = remember { mutableStateOf(ItemInfo.BEACON) }

    var latestSelectedItem by remember { mutableStateOf<ItemDto?>(null) }

    var witherHeartPoints by remember { mutableStateOf(5) }

    var updateCounter by remember { mutableStateOf(0) }
    val inventory by remember { mutableStateOf(Inventory()) }
    val inventoryItems = remember { mutableStateListOf<ItemDto>() }

    LaunchedEffect(updateCounter) {
        scope.launch(Dispatchers.IO) {
            inventory.initFromDatabase()

            val list = arrayListOf<ItemDto>()
            for (i in (inventory.size - 3)..inventory.size) {
                val item = inventory.items.find { v -> v.position == i } ?: ItemDto(
                    Item.AIR,
                    0,
                    i,
                    inventory.getId()
                )
                list.add(item)
            }
            inventoryItems.clear()
            inventoryItems.addAll(list)
        }
    }

    DisposableEffect(rememberSystemUiController()) {
        for (i in (inventory.size - 3)..inventory.size) {
            inventoryItems.add(ItemDto(Item.AIR, 0, i, inventory.getId()))
        }
        scope.launch(Dispatchers.IO) {
            inventory.initFromDatabase()
        }
        onDispose { }
    }

    if (showInventoryDialog.value)
        InventoryDialog(setShowDialog = {
            updateCounter++
            showInventoryDialog.value = it
        })

    if (showCraftingDialog.value)
        CraftingTableDialog(setShowDialog = {
            updateCounter++
            showCraftingDialog.value = it
        }, context)

    if (showSmeltingDialog.value)
        FurnaceDialog(setShowDialog = {
            updateCounter++
            showSmeltingDialog.value = it
        }, context)

    if (showChestDialog.value)
        ChestInventoryDialog(setShowDialog = {
            updateCounter++
            showChestDialog.value = it
        }, lastChestOpened.value, context)

    if (showNewItemDialog.value)
        ItemInfoDialog(
            setShowDialog = {
                showNewItemDialog.value = it
            },
            lastNewItem.value
        )

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
            planeRenderer = false,
            onCreate = { arSceneView ->
                val scope = arSceneView.lifecycleScope
                val chestNode = CreateNode("chest", context, scope)
                val enderChestNode = CreateNode("ender_chest", context, scope)
                val xmasChestNode = CreateNode("xmas_chest", context, scope)
                val craftingTableNode = CreateNode("crafting_table", context, scope)
                val furnaceNode = CreateNode("furnace", context, scope)
                val cowNode = CreateNode("cow", context, scope)
                val chickenNode = CreateNode("chicken", context, scope)
                val witherNode = CreateNode("wither", context, scope)
                val beaconNode = CreateNode("beacon", context, scope)
                val earthNode = CreateNode("earth", context, scope)

                craftingTableNode.onTap = { motionEvent, renderable ->
                    showCraftingDialog.value = true
                }
                chestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.ONE
                    openChestMP.start()
                    showChestDialog.value = true
                }
                enderChestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.TWO
                    openChestMP.start()
                    showChestDialog.value = true
                }
                xmasChestNode.onTap = { motionEvent, renderable ->
                    lastChestOpened.value = Chest.ChestType.THREE
                    openChestMP.start()
                    showChestDialog.value = true
                }
                furnaceNode.onTap = { motionEvent, renderable ->
                    showSmeltingDialog.value = true
                }
                beaconNode.onTap = { motionEvent, renderable ->
                     if(inventory.hasItem(Item.BEACON) && inventory.hasItem(Item.CAKE)){
                        navController.navigate("credits")
                     } else {
                        Toast.makeText(context, "Vuelve cuando consigas el Beacon y el Pastel", Toast.LENGTH_LONG).show()
                     }
                }

                chickenNode.onTap = { motionEvent, renderable ->
                    MediaPlayer.create(context, R.raw.chicken).start()
                    newItemMP.start()

                    lastNewItem.value = ItemInfo.GOT_NEW_EGG
                  
                    inventory.addItem(lastNewItem.value.item)
                    scope.launch(Dispatchers.IO) {
                        inventory.saveToDatabase()
                    }
                    showNewItemDialog.value = true
                }
                cowNode.onTap = { motionEvent, renderable ->
                    lastNewItem.value = ItemInfo.BEACON
                    inventory.addItem(lastNewItem.value.item)
                    if (latestSelectedItem?.item == Item.BUCKET) {
                        MediaPlayer.create(context, R.raw.cow).start()
                        MediaPlayer.create(context, R.raw.cow_milk).start()
                        newItemMP.start()

                        lastNewItem.value = ItemInfo.GOT_NEW_MILK_BUCKET
                        inventory.addItem(lastNewItem.value.item)
                        inventory.removeItem(latestSelectedItem!!.position)
                        scope.launch(Dispatchers.IO) {
                            inventory.saveToDatabase()
                            updateCounter++
                        }
                        showNewItemDialog.value = true
                    }
                }
                witherNode.onTap = { motionEvent, renderable ->
                    if (latestSelectedItem?.item == Item.DIAMOND_SWORD) {
                        if (witherHeartPoints > 0) {
                            //play wither hit sound
                            when (witherHeartPoints) {
                                5 -> MediaPlayer.create(context, R.raw.wither_hit_1).start()
                                4 -> MediaPlayer.create(context, R.raw.wither_hit_2).start()
                                3 -> MediaPlayer.create(context, R.raw.wither_hit_3).start()
                                2 -> MediaPlayer.create(context, R.raw.wither_hit_4).start()
                                1 -> MediaPlayer.create(context, R.raw.wither_hit_1).start()
                            }
                            witherHeartPoints--
                        } else if (witherHeartPoints == 0) {
                            MediaPlayer.create(context, R.raw.wither_death).start()
                            newItemBigMP.start()
                            lastNewItem.value = ItemInfo.GOT_NEW_NETHER_STAR
                            inventory.addItem(lastNewItem.value.item)
                            scope.launch(Dispatchers.IO) {
                                inventory.saveToDatabase()
                                updateCounter++
                            }
                            showNewItemDialog.value = true
                        }
                    }
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
                    config.lightEstimationMode = Config.LightEstimationMode.ENVIRONMENTAL_HDR
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
            },
        )
        ConstraintLayout(
            constraints, modifier = Modifier
                .fillMaxSize()
                .displayCutoutPadding()
        ) {
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
                            items(inventoryItems, key = { c -> c.position }) { itemDto ->
                                val item = itemDto.item
                                val quantity = itemDto.quantity
                                val position = itemDto.position
                                val imageBitmap = ImageBitmap.imageResource(item.image)
                                Surface(
                                    modifier = Modifier
                                        .clickable {
                                            latestSelectedItem = itemDto
                                        },
                                    color = if (latestSelectedItem == itemDto) Color(
                                        94,
                                        94,
                                        94
                                    )
                                    else Color(139, 139, 139)
                                ) {
                                    BoxWithConstraints(contentAlignment = Alignment.BottomEnd) {
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
                                        if (item != Item.AIR) {
                                            TextShadow(
                                                modifier = Modifier.padding(
                                                    end = 3.dp,
                                                    bottom = 3.dp
                                                ),
                                                text = quantity.toString(),
                                                fontWeight = FontWeight.Bold
                                            )
                                        }
                                    }
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
                                buttonClickedMP.start()
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
            TimeBox()
        }
    }
}
