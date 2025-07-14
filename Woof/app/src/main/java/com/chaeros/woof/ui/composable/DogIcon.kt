package com.chaeros.woof.ui.composable

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import com.chaeros.woof.R

@Composable
fun DogIcon(
    @DrawableRes dogIcon: Int,
    modifier: Modifier = Modifier
) {
    Image(
        modifier = modifier
            .size(dimensionResource(R.dimen.image_size))
            .padding(dimensionResource(R.dimen.padding_small))
            .clip(MaterialTheme.shapes.small),  // 이미지의 모서리를 입력한 값에 맞춰 둥글게 잘라냄
        contentScale = ContentScale.Crop,       // 이미지가 컴포넌트 크기에 맞게 잘리더라도 꽉 채워서 보여주는 방식
        painter = painterResource(dogIcon),
        contentDescription = null
    )
}