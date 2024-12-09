package com.example.challenge4.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp


@Composable
fun ReplyProfileImage(
    drawableResource: Int,
    description: String,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .drawBehind {
                val brush = Brush.radialGradient(
                    colors = listOf(
                        androidx.compose.ui.graphics.Color(0xFF00FF00),
                        androidx.compose.ui.graphics.Color(0xFF0000FF)
                    )
                )
                drawCircle(brush = brush, style = Stroke(width = 4.dp.toPx()))
            }
            .size(40.dp)
            .clip(CircleShape),
        painter = painterResource(id = drawableResource),
        contentDescription = description,
    )
}