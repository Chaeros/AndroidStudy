package com.chaeros.lemonade.ui.composable

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chaeros.lemonade.R
import com.chaeros.lemonade.ui.theme.Yellow

@Composable
fun LemonadeStepCard(modifier: Modifier = Modifier){
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        DescriptionTopBar(
            stringResource(R.string.app_name),
            Yellow
        )
        LemonadeDetailCard()
    }
}