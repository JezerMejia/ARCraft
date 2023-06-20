package com.jezerm.pokepc.ui.components

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.absoluteOffset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun TextShadow(
    text: String = "",
    modifier: Modifier = Modifier,
    style: TextStyle = MaterialTheme.typography.body1,
    textAlign: TextAlign = TextAlign.Start,
    fontWeight: FontWeight = FontWeight.Normal
) {
    BoxWithConstraints {
        Text(
            text,
            color = Color(50, 50, 50),
            modifier = modifier.absoluteOffset(1.5.dp, 1.4.dp),
            textAlign = textAlign,
            fontWeight = fontWeight,
            style = style
        )
        Text(
            text,
            modifier = modifier,
            color = Color.White,
            textAlign = textAlign,
            fontWeight = fontWeight,
            style = style
        )
    }
}

