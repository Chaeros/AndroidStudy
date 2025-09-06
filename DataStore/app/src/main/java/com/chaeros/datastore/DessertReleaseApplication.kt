package com.chaeros.datastore

import android.app.Application
import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.chaeros.datastore.data.UserPreferencesRepository

private const val LAYOUT_PREFERENCE_NAME = "layout_preferences"
// DataStore를 여러 개 만들 경우, 파일명이 충돌하지 않도록 생성 시 name 명시
// Context : 안드로이드의 앱 환경 정보 담고 있는 객체
// Context의 확장 프로퍼티를 정의하는 것으로, Activity 또는 Application 등에서 Context.dataStore라고 쓰면
// DataStore 인스턴스에 접근할 수 있도록 함
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = LAYOUT_PREFERENCE_NAME
)

// 앱 실행 시 가장 먼저 실행되는 Application 클래스 상속
// AndroidManifest.xml 에서 android:name 설정을 통해 시작 지점을 해당 클래스로 변경할 수 있음
class DessertReleaseApplication: Application() {
    // userPreferencesRepository는 DataStore를 감싸는 저장소로, Applcation 클래스 전역에서 사용 가능
    // lateinit을 통해 Application 생성 중 onCreate()에서 초기화 될 예정
    // [실행 흐름]
    // 1. 앱 실행
    // 2. Android 시스템에서 DessertReleaseApplication 생성
    // 3. onCreate() 실행하며 UserPreferencesRepository 준비
    //    -> 생성자 호출로 userPreferencesRepository에 객체 생성(메모리에 데이터 올림)
    // 4. 이후 MainActivity에서 해당 Repository 주입받아 UI와 연결
    //    -> Application 먼저 실행하여 앱 전역 초기화 이후, MainActivity가 실행되어 UI 초기화를 진행함
    lateinit var userPreferencesRepository: UserPreferencesRepository

    override fun onCreate() {
        super.onCreate()
        userPreferencesRepository = UserPreferencesRepository(dataStore)
    }
}