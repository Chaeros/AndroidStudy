package com.chaeros.diceroller

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaeros.diceroller.ui.theme.DiceRollerTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import com.chaeros.diceroller.ui.theme.Pink40

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DiceRollerTheme {
                DiceRollerApp()
            }
        }
    }
}

@Composable
fun DiceWithButtonAndImage(modifier: Modifier = Modifier){
    // Kotlin에서 by를 쓰기 위해서는
    // androidx.compose.runtime.getValue 와 setValue가 명시적으로 import 되어야함
    var result by remember { mutableStateOf(1) }
    val imageResource = when(result){
        1 -> R.drawable.dice_1
        2 -> R.drawable.dice_2
        3 -> R.drawable.dice_3
        4 -> R.drawable.dice_4
        5 -> R.drawable.dice_5
        6 -> R.drawable.dice_6
        else -> R.drawable.dice_6
    }
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally  // 열 내 하위 요소가 너비에 따라 기기 화면 중앙에 배치
    ) {
        Image(
            painter = painterResource(imageResource),
            contentDescription = "$result dice image"
        )
        Spacer(modifier = Modifier.padding(16.dp))
        Button(
            onClick={
                result = (1..6).random()
                Log.d("DiceRoller", "result = $result") // Emulator에서 구동하고 Logcat에서 확인 가능
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = Pink40,    // 배경색
                contentColor = Color.White  // 텍스트 색
            )
        ){
            Text(stringResource(R.string.roll))
        }
    }
}

@Preview
@Composable
fun DiceRollerApp(){
    DiceWithButtonAndImage(modifier = Modifier
        .fillMaxSize()                      // 레이아웃 내부의 구성요소가 전체를 차지
        .wrapContentSize(Alignment.Center)  // 구성요소가 세로와 가로로 모두 중앙에 배치됨
    )
}