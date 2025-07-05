package com.chaeros.myartspace.ui.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.chaeros.myartspace.R

@Composable
fun PreNextButtonCard(
    modifier: Modifier,
    onValueChangePreButton: () -> Unit,
    onValueChangeNext: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Button(onClick = onValueChangePreButton) {
            Text(stringResource(R.string.previous_button))
        }
        Spacer(modifier = Modifier.width(30.dp))
        Button(onClick = onValueChangeNext) {
            Text(stringResource(R.string.next_button))
        }
    }
}