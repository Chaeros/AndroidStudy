package com.chaeros.myartspace.ui.artwork

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.myartspace.data.imageList
import com.chaeros.myartspace.ui.common.ImageCard
import com.chaeros.myartspace.ui.common.PreNextButtonCard
import com.chaeros.myartspace.ui.common.TitleCard

@Composable
fun ArtworkLayout(
    modifier: Modifier
) {
    Column(
        modifier = modifier
    ) {
        var imageNumber by remember { mutableStateOf(0) }
        ImageCard(
            modifier = Modifier
            .weight(1f)
            .fillMaxWidth()
            .padding(10.dp),
            painterResource(
                imageList[imageNumber].imageResId),
                stringResource(imageList[imageNumber].imageDescriptionId)
        )
        TitleCard(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            mainTitle = stringResource(imageList[imageNumber].titleResId),
            artist = stringResource(imageList[imageNumber].artistResId),
            date = stringResource(imageList[imageNumber].dateResId)
        )
        PreNextButtonCard(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            onValueChangePreButton = {imageNumber=calculateImageNumber(imageNumber,-1,imageList.size)},
            onValueChangeNext = {imageNumber=calculateImageNumber(imageNumber,1,imageList.size)}
        )
    }
}

fun calculateImageNumber(imageNumber: Int, diff: Int, listCount: Int): Int{
    if(imageNumber+diff<0) return listCount-1
    if(imageNumber+diff>=listCount) return (imageNumber+diff)%listCount
    return imageNumber+diff
}