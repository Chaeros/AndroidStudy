package com.chaeros.unscramble.ui.gamescreen.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme.colorScheme
import androidx.compose.material3.MaterialTheme.shapes
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.unscramble.R

@Composable
fun GameLayout(
    currentScrambledWord: String,
    userGuess: String,
    onUserGuessChanged: (String) -> Unit,   // UI에서 View Model로 데이터 전송
    onKeyboardDone: () -> Unit,             // UI에서 View Model로 데이터 전송
    modifier: Modifier = Modifier
) {
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Card(
        modifier = modifier,
        elevation = CardDefaults.cardElevation(defaultElevation = 5.dp) // Card의 테두리에 그림자를 생성 5.dp 크기만큼
    ) {
        Column(
            // Arrangement : 자식들 간의 간격과 위치
            // Alignment : 자식들의 정렬 위치
            verticalArrangement = Arrangement.spacedBy(mediumPadding),  // 자식 Composable 간 세로 배치에 영향(수직)
            horizontalAlignment = Alignment.CenterHorizontally,         // 자식 Composable 간 가로 배치에 영향(수평)
            modifier = Modifier.padding(mediumPadding)
        ) {
            Text(
                modifier = Modifier
                    .clip(shapes.medium)                    // 텍스트 배경의 모서리를 medium 만큼 잘라냄
                    .background(colorScheme.surfaceTint)    // 텍스트 뒤의 배경색으로 지정(surfaceTint는 약간 강조된 색)
                    .padding(horizontal = 10.dp, vertical = 4.dp)   // 텍스트 주변에 여백
                    .align(alignment = Alignment.End),
                text = stringResource(R.string.word_count, 0),
                style = typography.titleMedium, // 중간 크기의 제목 스타일
                color = colorScheme.onPrimary   // 텍스트 색상이 onPrimary로, 배경색이 Primary인 경우, 잘 보이게 대비되는 색
            )
            Text(
                text = currentScrambledWord,
                fontSize = 45.sp,
                style = typography.displayMedium
            )
            Text(
                text = stringResource(R.string.instructions),
                textAlign = TextAlign.Center,
                style = typography.titleMedium
            )
            // TextField와 달리 외곽선이 있는 텍스트 필드
            OutlinedTextField(
                value = userGuess, // 텍스트 필드에 현재 표시될 값
                singleLine = true,
                shape = shapes.large,
                modifier = Modifier.fillMaxWidth(),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = colorScheme.surface,    // 텍스트필드 포커스 있을 때
                    unfocusedContainerColor = colorScheme.surface,  // 텍스트필드 포커스 없을 때
                    disabledContainerColor = colorScheme.surface,   // 텍스트필드 비활성화 됐을 때
                ),
                onValueChange = onUserGuessChanged,    // 사용자가 입력 필드에 타이핑할 때 호출되는 람다
                label = { Text(stringResource(R.string.enter_your_word)) },
                isError = false,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = { onKeyboardDone() }
                )
            )
        }
    }
}