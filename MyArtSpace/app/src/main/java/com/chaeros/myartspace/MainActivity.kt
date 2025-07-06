package com.chaeros.myartspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaeros.myartspace.ui.artwork.ArtworkLayout
import com.chaeros.myartspace.ui.theme.MyArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyArtSpaceTheme {
                Scaffold() { innerPadding ->
                    Surface(
                        modifier = Modifier.fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        ArtworkLayout(
                            modifier = Modifier
                                .fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

// 세로 모드 Preview
@Preview(
    name = "Portrait",
    showBackground = true,
    widthDp = 360,
    heightDp = 640
)
// 가로 모드 Preview
@Preview(
    name = "Landscape",
    showBackground = true,
    widthDp = 640,
    heightDp = 360
)
@Composable
fun GreetingPreview() {
    MyArtSpaceTheme {
        ArtworkLayout(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}