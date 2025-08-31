package com.chaeros.inventory.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chaeros.inventory.data.Item
import com.chaeros.inventory.data.ItemsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class HomeViewModel(
    itemsRepository: ItemsRepository
) : ViewModel() {
    val homeUiState: StateFlow<HomeUiState> =
        itemsRepository.getAllItemsStream()
            .map { HomeUiState(it) }    // Flow<List<Item>> -> Flow<HomeUiState>로 변환
            .stateIn(                   // startIn 연산자 사용을 통해 Flow를 StateFlow로 변환(StateFlow는 항상 최신값 소유)
                scope = viewModelScope,     // viewModel이 살아있는 동안 Flow collect
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),   // 구독자가 있을 때만 데이터 수집
                initialValue = HomeUiState()    // UI 그려질 데이터 초기값 설정
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}

data class HomeUiState(val itemList: List<Item> = listOf())