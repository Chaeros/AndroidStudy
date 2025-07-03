package com.chaeros.tipcalculator

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performTextInput
import com.chaeros.tipcalculator.ui.theme.TipCalculatorTheme
import org.junit.Rule
import org.junit.Test
import java.text.NumberFormat

// 이전 test 폴더 하위의 테스트 클래스는 JVM 환경에서 동작하며 비즈니스 로직 검증을 단위 테스트로 수행
// 계측 테스트는 실제 Android 디바이스나 에뮬레이터 환경에서의 UI에서 통합 테스트를 실행하고 속도가 상대적으로 느림
class TipUITests {
    // @get:Rule -> Kotlin에서 JUnit의 테스트 Rule을 val 프로퍼티에 적용할 때 사용하는 어노테이션 (getter에 적용됨)
    // Rule : 테스트를 실행할 때 composeTestRule이 제공하는 Compose 환경 자동 준비와 테스트가 끝나면 정리까지 수행
    // composeTestRule -> Compose UI 테스트를 위한 테스트 도우미 객체
    // createComposeRule() -> Compose UI 테스트 환경을 구성하는 Rule 생성 함수
    @get:Rule
    val composeTestRule = createComposeRule()

    // 계측 테스트 : androidTest 하위에서 @Test 주석이 달린 메서드
    @Test
    fun calculate_20_percent_tip() {
        composeTestRule.setContent {
            TipCalculatorTheme {
                TipCalculatorCard()
            }
        }

        // 라벨 또는 placeholder에 "Bill Amount"라는 이름을 가진 TextField에 10 입력된 상태
        composeTestRule.onNodeWithText("Bill Amount")
            .performTextInput("10")
        composeTestRule.onNodeWithText("Tip Percentage")
            .performTextInput("20")
        val expectedTip = NumberFormat.getCurrencyInstance().format(2)
        // 화면에서 "Tip Amount: $expectedTip"의 문자열을 지닌 UI 요소를 찾고
        // 만약 없어 테스트가 실패하면 Fail : No node with this text was found. 출력
        composeTestRule.onNodeWithText("Tip Amount: $expectedTip").assertExists(
            "Fail : No node with this text was found."
        )
    }
}