package com.chaeros.mars.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.http.GET

private const val BASE_URL = "https://android-kotlin-fun-mars-server.appspot.com"

// // Retrofit 인스턴스는 생성 비용이 비싼 편이라 앱 전체에서 1개만 쓰는 것이 일반적
private val retrofit = Retrofit.Builder()
    .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
    .baseUrl(BASE_URL)
    .build()

interface MarsApiService {
    // suspend 키워드 사용하여 비동기로 안전하게 네트워크 호출
    @GET("photos")
    suspend fun getPhotos() : List<MarsPhoto>
}

// Kotlin에서 object 선언은 클래스와 달리 애플리케이션에서 단 한 번만 인스턴스 생성 보장(전역 접근 보장)
// by lazy 키워드를 통해 처음 사용될 떄만 초기화
// 앱 전역에서 동일한 Retrofit 서비스 인스턴스를 쓰기 위한 싱글톤 객체
//object MarsApi {
//    val retrofitService : MarsApiService by lazy {
//        retrofit.create(MarsApiService::class.java)
//    }
//}
// MarsPhotoRepository DI 적용을 통한 종속성 완화