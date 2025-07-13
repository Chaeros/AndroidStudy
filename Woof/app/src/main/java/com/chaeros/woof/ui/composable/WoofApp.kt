package com.chaeros.woof.ui.composable

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.chaeros.woof.R
import com.chaeros.woof.data.dogs

@Composable
fun WoofApp() {
    LazyColumn {
        items(dogs) {
            DogItem(
                dog = it,
                modifier = Modifier.padding(dimensionResource(R.dimen.padding_small))   // 내부 카드 간 padding 생성
            )
        }
    }
}