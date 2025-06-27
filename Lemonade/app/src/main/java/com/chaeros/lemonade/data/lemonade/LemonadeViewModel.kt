package com.chaeros.lemonade.data.lemonade

import android.util.Log
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class LemonadeViewModel : ViewModel() {
    private val _result = MutableStateFlow(0)
    val result: StateFlow<Int> = _result

    private var tabCount = 0
    private var successCount = (2..4).random()

    fun onLemonStepClicked() {
        if (_result.value == 1) {
            tabCount++
            if (tabCount == successCount) {
                _result.value++
                tabCount = 0
                successCount = (2..4).random()
            }
        } else {
            _result.value++
            if (_result.value >= lemonSteps.size) _result.value = 0
        }
        Log.d("[Click Log]", "result = ${_result.value}, tabCount = $tabCount") // Emulator에서 구동하고 Logcat에서 확인 가능
    }
}