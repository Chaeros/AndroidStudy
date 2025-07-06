package com.chaeros.affrimation.domain.affirmation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import com.chaeros.affrimation.domain.affirmation.composable.AffirmationList
import com.chaeros.affrimation.domain.affirmation.data.Datasource

@Composable
fun AffirmationApp() {
    val layoutDirection = LocalLayoutDirection.current  // 현재 기기의 상태가 가로인지 세로인지 판단
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .statusBarsPadding()    // 노치나 투명바 등이 있는 기기 상단의 상태바와 겹치지 않도록 하는데 유용
            .padding(
                // 기기 마다 UI 요소가 보이긴 하지만 터치하기 어렵거나,
                // 보여야 할 정보가 잘리거나,
                // 시각적으로 불편하게 배치되는 걸 막기 위해 Safe Area를 계산해 컨텐츠 잘리지 않도록 패딩 설정
                start = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateStartPadding(layoutDirection),
                end = WindowInsets.safeDrawing.asPaddingValues()
                    .calculateEndPadding(layoutDirection),
            )

    ) {
        AffirmationList(
            Datasource().loadAffirmations()
        )
    }
}