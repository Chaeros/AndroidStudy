package com.chaeros.dessertclicker.viewmodel

data class DessertClickerUiState (
    var revenue : Int = 0,
    var dessertsSold : Int = 0,
    val currentDessertIndex : Int = 0,
    var currentDessertPrice : Int = 0,
    var currentDessertImageId : Int = 0
)