package com.chaeros.mars.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

// MarsPhoto 클래스를 직렬화할 수 있도록 함
@Serializable
data class MarsPhoto(
    val id: String,
    @SerialName(value = "img_src")
    val imgSrc: String // 실제 데이터 형식은 img_src이지만, 카멜 표기법 적용하고 싶을 때
)