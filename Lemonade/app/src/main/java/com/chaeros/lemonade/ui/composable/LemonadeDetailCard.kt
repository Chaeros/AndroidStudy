package com.chaeros.lemonade.ui.composable

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.lemonade.data.lemonade.LemonadeViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chaeros.lemonade.data.lemonade.lemonSteps
import androidx.compose.ui.graphics.Color
import com.chaeros.lemonade.ui.theme.Green

@Composable
fun LemonadeDetailCard(
    modifier: Modifier = Modifier,
    viewModel: LemonadeViewModel = viewModel()
    )
{
    val result by viewModel.result.collectAsState()
    val currentStep = lemonSteps.getOrNull(result) ?: lemonSteps.last()

    Column(
        modifier = modifier
            .padding(bottom = 300.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Button(
            onClick={
                viewModel.onLemonStepClicked()
            },
            // 버튼 색상 제거
            colors = ButtonDefaults.buttonColors(
                containerColor = Color.Transparent
            ),
            contentPadding = PaddingValues(0.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(200.dp)  // 전체 크기 제한
                    .clip(RoundedCornerShape(24.dp))    // 둥근 사각형
                    .background(Green)     // 연한 초록색 배경
                    .padding(16.dp), // 내부 여백
                contentAlignment = Alignment.Center
            ){
                Image(
                    painter = painterResource(currentStep.imageResource),
                    contentDescription = stringResource(currentStep.imageDescriptionResource),
                    modifier = Modifier
                        .size(120.dp)
                )
            }
        }
        Spacer(Modifier.padding(bottom=16.dp))
        Text(
            text = stringResource(currentStep.contentResource),
            modifier = modifier.padding(16.dp),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}