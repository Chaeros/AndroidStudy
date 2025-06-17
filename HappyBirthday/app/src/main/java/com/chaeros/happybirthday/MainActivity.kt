package com.chaeros.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaeros.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HappyBirthdayTheme {
                Scaffold( modifier = Modifier.fillMaxSize() ) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

// @Composable 어노테이션이 있는 함수는 반드시 첫 글자가 대문자인 파스칼 표기법 사용
// Compose 함수 작명법은 아래와 같다
// 1. UI 컴포넌트는 명사로 명명해야 한다. : DoneButton()
// 2. 동사 또는 동사구 x : DrawTextField()
// 3. 명사화된 전치사 x : TextFieldWithLink()
// 4. 형용사 x : Bright()
// 5. 부사 x : Outside()
// 6. 형용사 + 명사 o : RoundIcon()
@Preview(showBackground = true) // @Preview : Design 창에서 미리보기 할 때 사용, 실제 앱에는 반영 x
@Composable
fun BirthdayCardPreview() {
    HappyBirthdayTheme {
        Greeting("Chaeros")
    }
}