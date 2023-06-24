package com.jezerm.pokepc.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.jezerm.pokepc.ui.modifiers.outsetBorder

@Composable
fun TimeBox() {
    val grayColor = Color(198, 198, 198)


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