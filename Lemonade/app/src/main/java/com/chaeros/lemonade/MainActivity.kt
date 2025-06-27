package com.chaeros.lemonade

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import com.chaeros.lemonade.ui.composable.LemonadeStepCard
import com.chaeros.lemonade.ui.theme.LemonadeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LemonadeTheme {
                LemonadeStepCard(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}