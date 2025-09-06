package com.chaeros.datastore.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.chaeros.datastore.DessertReleaseApplication
import com.chaeros.datastore.R
import com.chaeros.datastore.data.UserPreferencesRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DessertReleaseViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    // 기존 상태 코드
    // UI 상태를 메모리에만 저장해둠, 휘발성 상태값으로 앱이 켜져 있는 동안에만 유지됨
    // StateFlow : 읽기 전용 상태 스트림
    // -> UI에서 해당 StateFlow를 collect나 collectAsState()를 통해 구독하면,
    //    상태가 변하는 값을 자동으로 최신 값을 emit 받을 수 있음(Hot Stream)
    //    여긴 viewModel 단이기 떄문에 구독하는 코드가 없는데, UI에서 구독해서 사용함.(여기서 쓰면 Flow 낭비)
    // MutableStateFlow : StateFlow의 구현체면서 쓰기 가능함 상태 스트림
    // [아래 코드에서 _uiState와 uiState를 구분지은 이유]
    // - _uiState는 MutableStateFlow로 변경가능하고, uiState는 StateFlow로 읽기만 가능
    // - 그리고 _uiState는 private으로 외부에서 접근불가한데,
    // - 외부 UI나 클래스에서는 uiState로만 접근 가능하며 읽기만 수행할 수 있다.
    // - 따라서 _uiState 내부의 값은 ViewModel 내부에서만 변경 가능하기에 ViewModel이 상태 변경의 단일 진입점이됨
    // -> 데이터 무결성 보장
    // 이게 MVVM 모델 (Model - View - ViewModel)
    // 1. Model(DB, DataStore,Repository), 2. View(UI), 3. ViewModel(View와 Model 사이 계층)
    // 사용자 버튼클릭 -> ViewModel에서 DataStore 데이터 변경 메서드 호출 -> DataStore 값 변경
    // -> Repository에서 DB를(DataStore) 구독중이었기에 상태 변경을 감지하고 ViewModel 업데이트
    // -> UI가 VIewModel을 구독중이엇기에, 상태 변경 감지하고 화면 재구성
    /*
    private val _uiState = MutableStateFlow(DessertReleaseUiState())
    val uiState: StateFlow<DessertReleaseUiState> = _uiState
     */
    
    // 변경한 상태 코드
    // DataStore에서 읽은 영구 저장 데이터를 불러옴
    // .map{} 을 통해 Boolean을 DessertReleaseUiState로 변환
    // .stateIn()을 통해 Flow를 StateFlow(Hot Stream)으로 변환 -> 항상 최신 상태 유지함.
    // -> 즉 UI가 uiState를 구독하다가, DataStore 값이 변하면 자동으로 업데이트 수행함
    // 아래 코드는 DB의(현재는 DataStore) 값이 변하는 것을 감지하고 ViewModel 값을 업데이트함
    // -> 여기서 업데이트 된 값을 UI가 구독중이기 때문에 UI에도 변경이 발생하는 것
    val uiState: StateFlow<DessertReleaseUiState> =
        userPreferencesRepository.isLinearLayout.map { isLinearLayout ->
            DessertReleaseUiState(isLinearLayout)
        }
    .stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = DessertReleaseUiState()
    )

    fun selectLayout(isLinearLayout: Boolean) {
        // 아래 코드만 있는 경우에는 앱이 실행 중일 떄만 상태가 유지됨(일시적 상태 변경)
        // 앱 새로 시작시에 항상 기본값으로 시작됨, UI에 값 즉시 반영
        // _uiState.value = DessertReleaseUiState(isLinearLayout)

        // 변경 코드는 DataStore에 영구 저장함.
        // UI에 값을 즉시 반영하지 않음
        viewModelScope.launch {
            // DataStore에 접근하는 작업은 suspend 함수로 비동기식으로 실행시킨다.
            // 파일 I/O 접근은 속도가 오래 걸릴 수 있으므로 비동기로 하는게 바람직
            // -> saveLayoutPreference() 메서드 들어가보면 suspend
            userPreferencesRepository.saveLayoutPreference(isLinearLayout)
        }
    }

    // companion object: 클래스에 속한 싱글톤 객체
    // viewModelFactory {} : 팩토리 DSL
    // -> 보통 Activity/Fragment/Compose에서 viewModel() 함수를 통해 ViewModel 생성
    // -> 하지만, 이 때 기본 생성자만 호출 가능함. 따라서 의존성이 필요하면 Factory를 써야함
    // + DSL(Domain Specific Language) : 편리성과 코드 간편화를 위해 JetPack 라이브러리에서 제공하는 함수
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // initializer : ViewModel을 어떻게 생성할지 정의
            // -> 마지막 줄에 클래스에서 사용하는 해당 ViewModel을 반환시킨다.
            initializer {
                // this[APPLICATION_KEY]에서 APPLICATION_KEY는 현재 앱의 Application 인스턴스를 꺼내올 수 있게 해줌
                // AndroidManifest.xml에서 Application 시작점을 DessertReleaseApplication로 두었기에 해당 인스턴스 꺼내옴
                val application = (this[APPLICATION_KEY] as DessertReleaseApplication)
                DessertReleaseViewModel(application.userPreferencesRepository) // -> ViewModel에 repository 전달
            }
        }
    }
}

/*
 * Data class containing various UI States for Dessert Release screens
 */
data class DessertReleaseUiState(
    val isLinearLayout: Boolean = true,
    val toggleContentDescription: Int =
        if (isLinearLayout) R.string.grid_layout_toggle else R.string.linear_layout_toggle,
    val toggleIcon: Int =
        if (isLinearLayout) R.drawable.ic_grid_layout else R.drawable.ic_linear_layout
)