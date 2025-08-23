package com.chaeros.mars.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.chaeros.mars.R
import com.chaeros.mars.network.MarsPhoto

@Composable
fun MarsPhotoCard(photo: MarsPhoto, modifier: Modifier = Modifier) {
    // 이미지 요청을 비동기식으로 실행하고 결과를 렌더링하는 컴포저블
    AsyncImage(
        model = ImageRequest.Builder(context = LocalContext.current)
            .data(photo.imgSrc)
            .crossfade(true) // 요청이 성공적으로 완료될 때, 크로스페이드 애니메이션 적용
            .build(),
        contentDescription = stringResource(R.string.mars_photo),
        contentScale = ContentScale.Crop,   // 이미지가 잘려도 화면 전체를 차지하도록 함
        error = painterResource(R.drawable.ic_broken_image),   // 이미지 로드 실패 시 이미지
        placeholder = painterResource(R.drawable.loading_img), // 이미지 로딩하는 중 이미지
        modifier = modifier.fillMaxWidth()
    )
}