package com.chaeros.unscramble.ui.gamescreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.unscramble.R
import com.chaeros.unscramble.ui.gamescreen.composable.GameLayout
import com.chaeros.unscramble.ui.gamescreen.composable.GameStatus
import com.chaeros.unscramble.viewmodel.GameViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.chaeros.unscramble.ui.gamescreen.composable.FinalScoreDialog

@Composable
fun GameScreen(
    gameViewModel: GameViewModel = viewModel()
) {
    val gameUiState by gameViewModel.uiState.collectAsState()       // uiState 값이 변경될 때마다 gameUiState 값을 사용하여 구성 가능함 함수가 재구성
    val mediumPadding = dimensionResource(R.dimen.padding_medium)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState())
            .safeDrawingPadding()
            .padding(mediumPadding),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = stringResource(R.string.app_name),
            style = typography.titleLarge,
        )
        GameLayout(
            currentScrambledWord = gameUiState.currentScrambledWord,    // ViewModel에서 UI로 데이터 흐름(맞춰야할 문자열)
            userGuess = gameViewModel.userGuess,                        // ViewModel에서 UI로 데이터 흐름(사용자 입력 문자열)
            wordCount = gameUiState.currentWordCount,
            onUserGuessChanged = { gameViewModel.updateUserGuess(it) },
            onKeyboardDone = { gameViewModel.checkUserGuess() },
            isGuessWrong = gameUiState.isGuessedWordWrong,
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(mediumPadding)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(mediumPadding),
            verticalArrangement = Arrangement.spacedBy(mediumPadding),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                onClick = { gameViewModel.checkUserGuess() }    // 람다 표현식 안에서 gameViewModel.checkUserGuess() 호출
            ) {
                Text(
                    text = stringResource(R.string.submit),
                    fontSize = 16.sp
                )
            }

            OutlinedButton(
                onClick = { gameViewModel.skip() },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(R.string.skip),
                    fontSize = 16.sp
                )
            }
        }

        GameStatus(score = gameUiState.score, modifier = Modifier.padding(20.dp))

        // ViewModel 에서 관리하는 isGameOver 변수가 true면 종료 다이얼로그 호출
        if (gameUiState.isGameOver){
           FinalScoreDialog(
               score = gameUiState.score,
               onPlayAgain = { gameViewModel.resetGame() }
           )
        }
    }
}