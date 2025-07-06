package com.chaeros.myartspace.ui.artwork

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration

@Composable
fun ArtworkLayout(
    modifier: Modifier
) {
    var imageNumber by remember { mutableStateOf(0) }
    val configuration = LocalConfiguration.current
    val isLandscape = configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeArtworkLayout(
            modifier = modifier,
            imageNumber = imageNumber,
            onChangeImageNumber = {imageNumber=it}  // LandscapeArtworkLayout Composable 내부의 onChangeImageNumber 메서드를 통해 결과 it 변수로 전달 받음
        )
    }
    else{
        PortraitArtworkLayout(
            modifier = modifier,
            imageNumber = imageNumber,
            onChangeImageNumber = {imageNumber=it}
        )
    }
}