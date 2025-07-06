package com.chaeros.myartspace.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp

@Composable
fun ImageCard(
    modifier: Modifier = Modifier,
    painter: Painter,
    imageDescription: String
){
    Image(
        modifier = modifier,
        painter = painter,
        contentDescription = imageDescription
    )
}