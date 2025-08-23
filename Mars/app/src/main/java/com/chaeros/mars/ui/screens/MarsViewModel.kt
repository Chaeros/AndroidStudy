package com.example.marsphotos.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.chaeros.mars.MarsPhotoApplication
import com.chaeros.mars.data.MarsPhotoRepository
import com.chaeros.mars.network.MarsPhoto
import kotlinx.coroutines.launch
import java.io.IOException

// sealed 키워드를 통해 MarsUiState interface를 내부에서만 implements 또는 extends 할 수 잇음
sealed interface MarsUiState {
    data class Success(val photos: List<MarsPhoto>) : MarsUiState
    object Error : MarsUiState
    object Loading : MarsUiState
}

class MarsViewModel(
    private val marsPhotoRepository: MarsPhotoRepository
) : ViewModel() {
    var marsUiState: MarsUiState by mutableStateOf(MarsUiState.Loading)
        private set

    // init: 초기화 블록
    // 생성자가 끝난 직후 실행되는 추가 작업 블록
    init {
        getMarsPhotos()
    }

    // 네트워크 같은 I/O는 반드시 비동기로 작성하여 메인 스레드 차단 금지
    private fun getMarsPhotos() {
        // launch 메서드를 통해 코루틴 실행
        viewModelScope.launch {
            marsUiState =try {
                MarsUiState.Success(marsPhotoRepository.getMarsPhotos())
            } catch (e:IOException) {
                MarsUiState.Error // 반환 타입을 try와 catch문 동일하게 일치시켜줘야 에러가 발생하지 않음
            }
        }
    }

    // 기본 object는 전체에서 하나, companion object는 클래스에서 하나
    // companion 객체를 사용하면 비용이 많이 드는 객체의 새 인스턴스르 만들 필요 없이 모두가 단일 객체 인스턴스를 사용할 수 있다
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            // initializer : viewModelFactory 안에서 ViewModel을 생성할 때 실행되는 블록(커스텀 viewModel 생성기)
            // 사용하는 이유 : ViewModel은 기본 생성자 밖에 사용 못함, 그런데 의존성 주입으로 Repository를 넣어주기 위해서 initializer 사용하여 viewModel 생성
            initializer {
                // APPLICATION_KEY : ViewModelProvider.AndroidViewModelFactory.Companion 객체의 일부이며 앱의 MarsPhotosApplication 객체를 찾는 데 사용
                val application = (this[APPLICATION_KEY] as MarsPhotoApplication)
                val marsPhotosRepository = application.container.marsPhotosRepository
                MarsViewModel(marsPhotoRepository = marsPhotosRepository)
            }
        }
    }
}
