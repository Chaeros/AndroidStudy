package com.chaeros.affrimation.domain.affirmation.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chaeros.affrimation.domain.affirmation.card.AffirmationCard
import com.chaeros.affrimation.domain.affirmation.model.Affirmation

@Composable
fun AffirmationList(
    affirmationList: List<Affirmation>,
    modifier: Modifier = Modifier
){
    // 한 번에 모든 list들을 UI에 올리지 않고
    // 스크롤을 통해 필요한 만큼의 item을 추가하는 Column
    LazyColumn(
        modifier = modifier
    ) {
        // items: LazyColumn에 List 내의 항목을 추가하는 메서드
        items(affirmationList) { affirmation ->
            AffirmationCard(
                affirmation = affirmation,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}