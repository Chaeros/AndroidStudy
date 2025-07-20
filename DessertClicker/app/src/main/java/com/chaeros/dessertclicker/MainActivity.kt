package com.chaeros.dessertclicker

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.chaeros.dessertclicker.data.Datasource
import com.chaeros.dessertclicker.ui.composable.DessertClickerApp
import com.chaeros.dessertclicker.ui.theme.DessertClickerTheme

// 로그가 발생하는 지점을 통일하기 위해 상수 사용
private const val TAG = "MainActivity"

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        Log.d(TAG,"onCreate Called")
        setContent {
            DessertClickerTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .statusBarsPadding()
                ) {
                    DessertClickerApp(desserts = Datasource.dessertList)
                }
            }
        }
    }

    // The Activity Lifecycle
    // 소괄호 안은 명령, 소괄호 없으면 Activity 상태
    // Initialized -> (onCreated) -> Created -> (onStart, onRestart) -> Started -> (onResume) -> Resumed
    // Initialized <- (onDestroy) <- Created <-       (onStop)       <- Started <- (onPause)  <- Resumed
    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart Called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume Called")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG,"onRestart Called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause Called")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop Called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy Called")
    }
}