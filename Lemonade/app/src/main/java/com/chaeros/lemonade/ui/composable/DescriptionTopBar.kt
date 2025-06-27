package com.chaeros.lemonade.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DescriptionTopBar(
    title: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    // Text 같은 경우 수직 정렬이 없다, 따라서 수직 정렬을 해주기 위해서는 Box로 감싸야함
    Box(
        modifier = modifier
            .background(backgroundColor)
            .fillMaxWidth()
            .height(50.dp),
        contentAlignment = Alignment.Center // 수직, 수평 중앙 정렬
    ) {
        Text(
            text = title,
            textAlign = TextAlign.Center,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
    }
}