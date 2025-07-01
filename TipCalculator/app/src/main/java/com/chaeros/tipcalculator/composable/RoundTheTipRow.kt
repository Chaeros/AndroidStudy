package com.chaeros.tipcalculator.composable

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.tipcalculator.R

// 정수 형태로 반올림하는 Composable
@Composable
fun RoundTheTipRow(
    roundUp: Boolean,
    onRoundUpChanged: (Boolean) -> Unit,    // 콜백 함수 불러오기
    modifier: Modifier = Modifier
){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .size(48.dp),
        verticalAlignment = Alignment.CenterVertically  // 수평 정렬
    ){
        Text(
            text = stringResource(R.string.round_up_tip)
        )
        Switch(
            modifier = modifier
                .fillMaxWidth()
                .wrapContentWidth(Alignment.End),   // 스위치 위치를 Composable 화면 오른쪽 끝에 맞춤
            checked = roundUp,                      // 스위치 선택 여부
            onCheckedChange = onRoundUpChanged,     // 스위치 클릭시 호출될 콜백 함수
        )
    }
}