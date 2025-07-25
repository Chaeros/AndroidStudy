package com.chaeros.unscramble.ui.gamescreen.composable

import android.app.Activity
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.chaeros.unscramble.R

@Composable
fun FinalScoreDialog(
    score: Int,
    onPlayAgain: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val activity = context as? Activity // context가 Activity가 아니면 null 반환, LocalContext.current가 Activity가 아닐 수도 있으므로

    AlertDialog(
        onDismissRequest = {},  // 다이얼로그에서 외부 영역을 클릭하거나, 뒤로 가기 버튼을 눌렀을 때 동작
        title = { Text(text = stringResource(R.string.congratulations)) },
        text = { Text(text = stringResource(R.string.you_scored, score)) },
        modifier = modifier,
        dismissButton = {
            TextButton(
                onClick = {
                    activity?.finish()
                }
            ) {
                Text(text = stringResource(R.string.exit))
            }
        },
        confirmButton = {
            TextButton(onClick = onPlayAgain) {
                Text(text = stringResource(R.string.play_again))
            }
        }
    )
}