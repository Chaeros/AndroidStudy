package com.chaeros.unscramble.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.flow.MutableStateFlow
import androidx.lifecycle.ViewModel
import com.chaeros.unscramble.data.allWords
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

// ViewModel 사용의 이점
// 1. UI 상태 보존 : 화면 회전이나 재구성(Configuration Change) 이 발생해도 데이터를 유지
// 2. UI 로직 분리 : UI와 상태 처리/게임 로직을 분리함으로써 클린 아키텍처에 가까운 설계
// 3. Lifecycle에 안전 : Activity나 Fragment의 생명주기에 종속되지 않고, 메모리 누수나 부적절한 상태 갱신을 방지
class GameViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(GameUiState())          // 	ViewModel 내부에서만 상태 수정
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()    // 	읽기 전용으로 UI에 노출
    private lateinit var currentWord: String                        // 선언 시 값을 지정하지 않아도 되며, 이후 사용전 반드시 초기화해야 함
    private var usedWords: MutableSet<String> = mutableSetOf()
    var userGuess by mutableStateOf("")

    // 	ViewModel 초기화 시 상태 설정
    init {
        resetGame()
    }

    private fun pickRandomWordAndShuffle(): String {
        currentWord = allWords.random()
        if (usedWords.contains(currentWord)) {
            return pickRandomWordAndShuffle()
        } else {
            usedWords.add(currentWord)
            return shuffleCurrentWord(currentWord)
        }
    }

    private fun shuffleCurrentWord(word: String): String {
        val tempWord = word.toCharArray()   // word를 CharArray로 변경 ex)"apple" → ['a', 'p', 'p', 'l', 'e']
        tempWord.shuffle()                  // 배열 내 요소들을 랜덤하게 섞음
        while (String(tempWord).equals(word)) {
            tempWord.shuffle()
        }
        return String(tempWord)
    }

    fun resetGame() {
        usedWords.clear()
        _uiState.value = GameUiState(currentScrambledWord = pickRandomWordAndShuffle())
    }

    fun updateUserGuess(guessedWord: String){
        userGuess = guessedWord
    }
}