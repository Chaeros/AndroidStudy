package com.chaeros.datastore

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.chaeros.datastore.ui.DessertReleaseApp
import com.chaeros.datastore.ui.theme.DataStoreTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DataStoreTheme {
                DessertReleaseApp()
            }
        }
    }
}