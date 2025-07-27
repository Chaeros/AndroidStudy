package com.chaeros.dessertclicker.viewmodel

import androidx.lifecycle.ViewModel
import com.chaeros.dessertclicker.data.Datasource
import com.chaeros.dessertclicker.model.Dessert
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class DessertClickerViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DessertClickerUiState())
    val uiState: StateFlow<DessertClickerUiState> = _uiState.asStateFlow()
    val desserts = Datasource.dessertList

    init {
        resetViewModel()
    }

    private fun resetViewModel(){
        val currentDessertIndex = _uiState.value.currentDessertIndex;
        _uiState.value = DessertClickerUiState(
            currentDessertPrice = desserts[currentDessertIndex].price,
            currentDessertImageId = desserts[currentDessertIndex].imageId
        )
    }

    fun updateRevenue(price: Int){
        // _uiState.value.revenue += price
        // 만약 위와 같이 copy를 사용하지 않고 내부 값을 직접 변경하려 시도하면,
        // StateFlow는 불변 객체로 UI는 변경되었음을 전혀 감지할 수 없다.
        _uiState.update { currentState ->
            currentState.copy(
                revenue = _uiState.value.revenue +price
            )
        }
    }

    fun increaseDessertSoldCount(){
        _uiState.update { currentState ->
            currentState.copy(
                dessertsSold = currentState.dessertsSold.inc()
            )
        }
    }

    fun updateCurrentDessert(dessert: Dessert) {
        _uiState.value = _uiState.value.copy(
            currentDessertImageId = dessert.imageId,
            currentDessertPrice = dessert.price
        )
    }
}