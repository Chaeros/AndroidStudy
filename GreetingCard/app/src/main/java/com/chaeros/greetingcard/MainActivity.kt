package com.chaeros.greetingcard

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaeros.greetingcard.ui.theme.GreetingCardTheme

class MainActivity : ComponentActivity() {
    // Android 앱의 진입점, Kotlin의 main() 함수격
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        // setContent : layout 정의하는데 사용
        setContent {
            GreetingCardTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

// Composable 함수
// Jetpark Compose에서 UI를 선언적으로 구성하기 위한 함수
// XML 레이아웃을 대체하며 Text(), Button(), Column() 같은 UI 컴포넌트 직접 작성 가능하게 해줌, 보통 반환값 없음
// 일반 함수는 JVM에서 즉시 실행되지만, Composable 함수는 Compose 런타임이 조절해서 호출, 자동으로 상태 기반 재구성 지원
// 1. 함수 앞에 @Composable 주석 추가
// 2. @Composable 함수 이름은 대문자로 표기
// 3. @Composable 함수는 아무것도 반환 x
@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    // Surface()를 통해 내부 Container의 배경색을 지정할 수 있다
    Surface(color= Color.Blue) {
        Text(
            text = "Hi, my name is $name!",
            // Text Container 내부의 color는 글자 색을 하얀색으로 변환
            color=Color.White,
            // modifier는 Composable을 강화하는데 사용
            // 예로 padding을 사용하여 텍스트 주의 공백 발생 가능
            modifier = modifier.padding(24.dp)
        )
    }
}

// 실제 앱에 표시되는 내용들이 아닌, 미리보기에서 적용되는 내용
@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    GreetingCardTheme {
        Greeting("Galaxy")
    }
}