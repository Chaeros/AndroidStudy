package com.chaeros.happybirthday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.happybirthday.ui.theme.HappyBirthdayTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HappyBirthdayTheme {
                Surface(
                    // Modifier는 Composable 재사용성을 높이기 위해 외부에서 modifier를 받아 내부에 적용시킴
                    modifier = Modifier.fillMaxSize() ,
                    color = MaterialTheme.colorScheme.background
                ){
                    GreetingText(
                        message = "Happy Brithday Chaeros!",
                        from="your friend",
                        // 전체 Composable에 바깥 여백(padding) 적용
                        // GreetingText 내부 Column에 적용됨,
                        modifier = Modifier.padding(8.dp)
                    )
                }
            }
        }
    }
}

// 메시지를 지정된 인수로 전달하는 Text 컴포저블
// 글꼴 측정 단위는 dp, sp로 나뉜다
// 1. dp(Density-independent Pixels, 밀도 독립 픽셀)
// : 레이아웃 크기(넓이, 높이, 여백 등)에 사용, 화면 해상도가 달라도 동일한 물리적 크기 유지
// : 예로 Button의 높이를 48dp로 지정하면 저해상도/고해상도 device에서 비슷한 크기고 출력됨
// : 버튼, 카드, 여백, 마진, 패딩 등에 사용됨
// 2. sp(Scale-independent Pixels, 확장 가능한 픽셀)
// : 텍스트 크기에만 사용, 사용자 시스템에서 설정한 글꼴 크기에 따라 자동으로 배율이 조정됨
// : 예로 사용자가 device에서 '글꼴 크기=크게'로 설정해둔 경우 16sp가 16dp 보다 크게 렌더링됨
// : 접근성 고려한 반응형 텍스트 제공 가능(Text, TextView, TextField etc 사용)
// -> dp는 레이아웃에 사용, sp는 텍스트에 사용 -> 다양한 화면 크기와 사용자 설정에도 일관된 UX 보장 가능
@Composable
fun GreetingText(message: String, from: String, modifier: Modifier = Modifier) {
    // Compose 기본 표준 레이아웃 : Column, Row, Box
    // Composable 콘텐츠들이 겹치지 않게 하며, 배치할 때 사용
    // Row : 좌우 배치, Column : 상하 배치, Box : 겹치기 및 위치 지정 배치(예로 배경과 글을 겹치고 싶은 경우)
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = modifier.padding(8.dp)   // 결과적으로 같은 modifier에 똑같이 padding이 적용된 것, 중복 효과는 없어 마지막 설정만 적용
    ) {
        Text(
            text = message,                 // UI에 출력할 텍스트
            fontSize = 30.sp,               // font Size
            lineHeight = 116.sp,            // font 높이
            textAlign = TextAlign.Center    // text 가운데 정렬
        )
        Text(
            text = from,
            fontSize = 15.sp,
            modifier = Modifier                     // Text Composable만을 위해 새로운 Modifier 객체를 생성한 것
                .padding(16.dp)                     // from 텍스트 컴포넌트 자체의 안쪽 여백(padding) 설정
                .align(alignment = Alignment.End)   // from 텍스트를 Column 내부에서 오른쪽 끝으로 정렬
        )
    }
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
        // 위 Composable 함수를 호출하여 UI를 갱신한다.
        GreetingText(message = "Happy Brithday Chaeros!", from="From. your friend")
    }
}