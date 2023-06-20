package com.jezerm.pokepc.dialog

import androidx.annotation.Dimension
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jezerm.pokepc.entities.Inventory
import com.jezerm.pokepc.entities.Item
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.insetBorder
import com.jezerm.pokepc.ui.modifiers.outsetBorder

@Composable
fun ChestGrid(inventory: Inventory) {
    val items = ArrayList<Pair<Item, Int>>()

    for (i in 1..8) {
        items.add(Item.AIR to i)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .widthIn(100.dp, 164.dp)
            .heightIn(100.dp, 164.dp),
        columns = GridCells.Fixed(4),
        userScrollEnabled = false
    ) {
        items(items, key = { c -> c.second }) { (item, position) ->
            val imageBitmap = ImageBitmap.imageResource(item.image)
            Surface(color = Color(139, 139, 139)) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RectangleShape)
                        .insetBorder(lightSize = 4.dp, darkSize = 4.dp, borderPadding = 0.dp)
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

@Composable
fun InventoryGrid(inventory: Inventory) {
    val items = ArrayList<Pair<Item, Int>>()

    for (i in 1..20) {
        items.add(Item.AIR to i)
    }

    LazyVerticalGrid(
        modifier = Modifier
            .widthIn(100.dp, 224.dp)
            .heightIn(100.dp, 200.dp),
        columns = GridCells.Fixed(5),
        userScrollEnabled = false
    ) {
        items(items, key = { c -> c.second }) { (item, position) ->
            val imageBitmap = ImageBitmap.imageResource(item.image)
            Surface(color = Color(139, 139, 139)) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentHeight()
                        .clip(RectangleShape)
                        .insetBorder(lightSize = 4.dp, darkSize = 4.dp, borderPadding = 0.dp)
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

@Composable
fun ChestInventoryDialog(setShowDialog: (Boolean) -> Unit, chestName: String, inventory: Inventory) {
    val grayColor = Color(198, 198, 198)

    Dialog(onDismissRequest = { setShowDialog(false) }) {
        Surface {
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                shape = RectangleShape,
                backgroundColor = grayColor
            ) {
                Column(modifier = Modifier.padding(24.dp)) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(
                            "Cofre $chestName",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        ChestGrid(inventory)
                    }

                    Spacer(modifier = Modifier.height(24.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        TextShadow(
                            "Inventario",
                            style = MaterialTheme.typography.h3,
                            textAlign = TextAlign.Center
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        InventoryGrid(inventory)
                    }
                }
            }
        }
    }
}