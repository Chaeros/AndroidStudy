package com.chaeros.lemonade.ui.preview

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.chaeros.lemonade.ui.composable.LemonadeStepCard
import com.chaeros.lemonade.ui.theme.LemonadeTheme

@Preview(showBackground = true)
@Composable
fun LemonadePreview(){
    LemonadeTheme {
        LemonadeStepCard(
            modifier = Modifier
                .fillMaxSize()
        )
    }
}