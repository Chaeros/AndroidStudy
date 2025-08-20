package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeros.mars.network.MarsApi
import kotlinx.coroutines.launch
import java.io.IOException

// sealed 키워드를 통해 MarsUiState interface를 내부에서만 implements 또는 extends 할 수 잇음
sealed interface MarsUiState {
    data class Success(val photos: String) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    init {
        getMarsPhotos()
    }

    // 네트워크 같은 I/O는 반드시 비동기로 작성하여 메인 스레드 차단 금지
    private fun getMarsPhotos() {
        // launch 메서드를 통해 코루틴 실행
        viewModelScope.launch {
            try {
                val listResult = MarsApi.retrofitService.getPhotos()
                marsUiState = MarsUiState.Success(
                    "Success: ${listResult.size} Mars Photos retrieved"
                )  // Compose 상태 갱신, 화면의 Text 등이 자동 업데이트
            } catch (e:IOException) {
                marsUiState = MarsUiState.Error
            }
        }
    }
}
