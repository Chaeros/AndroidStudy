package com.chaeros.unscramble.ui.test

import com.chaeros.unscramble.data.SCORE_INCREASE
import com.chaeros.unscramble.data.allWords
import com.chaeros.unscramble.viewmodel.GameViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import org.junit.Assert.assertNotEquals
import org.junit.Test

// 각 테스트 마다 private val viewModel = GameViewModel() 가 새로 생성됨
// 즉, 테스트 함수(@Test) 하나마다 테스트 클래스의 새 인스턴스를 생성하고, 그 안의 필드들도 새로 초기화함.
// 따라서 테스트 간 상태를 공유하지 않고 격리됨
class GameViewModelTest {
    private val viewModel = GameViewModel()

    // 사용자가 입력한 데이터가 정답과 일치한 경우, 정상적인 점수가 오르는지 테스트
    @Test
    fun gameViewModel_CorrectWordGuessed_ScoreUpdatedAndErrorFlagUnset() {
        var currentGameUiState = viewModel.uiState.value
        val correctPlayerWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        viewModel.updateUserGuess(correctPlayerWord)
        viewModel.checkUserGuess()

        currentGameUiState = viewModel.uiState.value
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertEquals(SCORE_AFTER_FIRST_CORRECT_ANSWER, currentGameUiState.score)
    }

    @Test
    fun gameViewModel_IncorrectGuess_ErrorFlagSet() {
        val incorrectWord = "wrong"

        viewModel.updateUserGuess(incorrectWord)
        viewModel.checkUserGuess()

        var currentGameUiState = viewModel.uiState.value
        assertTrue(currentGameUiState.isGuessedWordWrong)
        assertEquals(0,currentGameUiState.score)
    }

    @Test
    fun gameViewModel_Initialization_FirstWordLoaded() {
        var currentGameUiState = viewModel.uiState.value
        val unScrambledWord = getUnscrambledWord(currentGameUiState.currentScrambledWord)

        assertNotEquals(unScrambledWord,currentGameUiState.currentScrambledWord)
        assertEquals(currentGameUiState.currentWordCount,1)
        assertEquals(currentGameUiState.score,0)
        assertFalse(currentGameUiState.isGuessedWordWrong)
        assertFalse(currentGameUiState.isGameOver)
    }

    fun getUnscrambledWord(scrambledWord: String): String {
        return allWords.first { word ->
            word.length == scrambledWord.length && word.toCharArray().sorted() == scrambledWord.toCharArray().sorted()  // toCharArray().sorted() 메서드를 통해 문자열을 각 철자 오름차순으로 정렬
        }
    }

    // companion object란? Java의 static 키워드와 유사한 개념
    // ex ) GameViewModelTest.SCORE_AFTER_FIRST_CORRECT_ANSWER 로 사용 가능(단, private이라 외부에선 접근 불가)
    // 클래스 바깥에 const 변수로 선언하지 않고 내부 companion object로 변수 선언하는 이유
    // - 다른 클래스에서 접근할 수 없도록 할 수 있음
    // - 테스트 코드 안에서만 의미있게 사용할 수 있음
    companion object {
        private const val SCORE_AFTER_FIRST_CORRECT_ANSWER = SCORE_INCREASE
    }
}