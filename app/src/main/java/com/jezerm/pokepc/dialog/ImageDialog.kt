package com.jezerm.pokepc.dialog

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.jezerm.pokepc.ui.components.TextShadow
import com.jezerm.pokepc.ui.modifiers.outsetBorder

@Composable
fun ImageDialog(
    setShowDialog: (Boolean) -> Unit,
    bitmap: ImageBitmap,
    contentDescription: String,
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
                        .padding(24.dp)
                        .fillMaxWidth()
                        .verticalScroll(rememberScrollState()),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Image(
                        modifier = Modifier
                            .border(BorderStroke(2.dp, Color.Black))
                            .widthIn(50.dp, Dp.Infinity)
                            .heightIn(50.dp, 500.dp),
                        bitmap = bitmap,
                        contentDescription = contentDescription,
                        contentScale = ContentScale.Fit,
                    )
                    TextShadow(
                        text = contentDescription,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
