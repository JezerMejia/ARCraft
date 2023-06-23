package com.jezerm.pokepc.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jezerm.pokepc.R
import com.jezerm.pokepc.ui.modifiers.outsetBorder
import com.jezerm.pokepc.ui.theme.PokePCTheme

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BorderedButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    content: @Composable RowScope.() -> Unit
) {
    val grayColor = Color(143, 143, 143)
    val blueColor = Color(136, 146, 201)
    val shape = RectangleShape

    val interactionSource = remember { MutableInteractionSource() }
    Box(
        modifier
            .semantics { role = Role.Button }
            .clickable(
                interactionSource = interactionSource,
                indication = rememberRipple(bounded = true, color = blueColor),
                onClick = onClick
            ),
        propagateMinConstraints = true
    ) {
        Surface(
            modifier = Modifier
                .clip(shape)
                .border(BorderStroke(2.dp, Color.Black), shape)
                .outsetBorder(),
            color = grayColor,
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(ButtonDefaults.ContentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                content()
            }
        }
    }
}

@Preview
@Composable
fun BorderedButtonPreview() {
    Surface(
        modifier = Modifier
            .widthIn(30.dp, 100.dp)
            .heightIn(30.dp, 50.dp)
    ) {
        PokePCTheme {
            BorderedButton(onClick = { }) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        modifier = Modifier.size(16.dp),
                        bitmap = ImageBitmap.imageResource(R.drawable.back),
                        contentDescription = "Atrás",
                        filterQuality = FilterQuality.None,
                        contentScale = ContentScale.FillWidth,
                        alignment = Alignment.Center
                    )
                    TextShadow(text = "Button", style = MaterialTheme.typography.button)
                }
            }
        }
    }
}