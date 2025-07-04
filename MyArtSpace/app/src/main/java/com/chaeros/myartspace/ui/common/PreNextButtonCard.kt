package com.chaeros.myartspace.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.chaeros.myartspace.R

@Composable
fun PreNextButtonCard(
    modifier: Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = {}) {
            Text(stringResource(R.string.previous_button))
        }
        Button(onClick = {}) {
            Text(stringResource(R.string.next_button))
        }
    }
}