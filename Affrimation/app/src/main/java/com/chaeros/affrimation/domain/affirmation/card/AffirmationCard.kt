package com.chaeros.affrimation.domain.affirmation.card

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.affrimation.domain.affirmation.model.Affirmation

@Composable
fun AffirmationCard(
    affirmation: Affirmation,
    modifier: Modifier = Modifier
) {
    // Card : 목록 항목을 만들 때 사용
    Card(
        modifier = modifier
    ) {
        Column {
            Image(
                painter = painterResource(affirmation.imageResourceId),
                contentDescription = stringResource(affirmation.stringResourceId),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp),
                contentScale = ContentScale.Crop // 이미지가 잘리더라도 할당받은 공간을 꽉 채우도록 함
            )
            Text(
                text = stringResource(affirmation.stringResourceId),
                modifier = modifier
                    .padding(10.dp),
                style = MaterialTheme.typography.headlineSmall  // Material Design3에서 정의된 표준 텍스트 스타일
            )
        }
    }
}