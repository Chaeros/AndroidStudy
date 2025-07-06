package com.chaeros.myartspace.ui.artwork

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.myartspace.data.imageList
import com.chaeros.myartspace.ui.common.ImageCard
import com.chaeros.myartspace.ui.common.PreNextButtonCard
import com.chaeros.myartspace.ui.common.TitleCard
import com.chaeros.myartspace.utils.image.calculateImageNumber

@Composable
fun LandscapeArtworkLayout(
    modifier : Modifier,
    imageNumber : Int,
    onChangeImageNumber: (Int) -> Unit  // 함수의 결과를 Int 타입으로 외부 Composable에 it 변수로 전달
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(1.dp)
    ){
        ImageCard(
            modifier = Modifier
                .weight(1f)
                .padding(10.dp),
            painterResource(imageList[imageNumber].imageResId),
            stringResource(imageList[imageNumber].imageDescriptionId)
        )
        Column(
            modifier = Modifier
                .weight(1f),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TitleCard(
                modifier = Modifier
                    .padding(16.dp),
                mainTitle = stringResource(imageList[imageNumber].titleResId),
                artist = stringResource(imageList[imageNumber].artistResId),
                date = stringResource(imageList[imageNumber].dateResId)
            )
            PreNextButtonCard(
                modifier = Modifier
                    .padding(16.dp),
                onValueChangePreButton = {onChangeImageNumber(calculateImageNumber(imageNumber,-1, imageList.size))},
                onValueChangeNext = {onChangeImageNumber(calculateImageNumber(imageNumber,1, imageList.size))}
            )
        }
    }
}