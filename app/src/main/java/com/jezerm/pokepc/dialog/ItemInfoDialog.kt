package com.jezerm.pokepc.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jezerm.pokepc.entities.ItemInfo
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.outsetBorder

@Composable
fun ItemInfoDialog(
    setShowDialog: (Boolean) -> Unit,
    itemInfo: ItemInfo
) {
    val grayColor = Color(198, 198, 198)

    Dialog(
        onDismissRequest = {
            setShowDialog(false)
        },
    ) {
        Surface {
            Card(
                modifier = Modifier
                    .clip(RectangleShape)
                    .outsetBorder(lightSize = 8.dp, darkSize = 10.dp, borderPadding = 0.dp),
                shape = RectangleShape,
                backgroundColor = grayColor
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp)
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .size(100.dp),
                        bitmap = ImageBitmap.imageResource(itemInfo.item.image),
                        contentDescription = itemInfo.item.value,
                        filterQuality = FilterQuality.None,
                        contentScale = ContentScale.FillWidth,
                    )
                    TextShadow(
                        text = itemInfo.item.value,
                        textAlign = TextAlign.Center,
                        style = MaterialTheme.typography.h4
                    )
                    TextShadow(
                        modifier = Modifier.padding(bottom = 8.dp),
                        text = itemInfo.description,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
