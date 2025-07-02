package com.chaeros.tipcalculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.VisibleForTesting
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.chaeros.tipcalculator.ui.theme.TipCalculatorTheme
import java.text.NumberFormat
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.chaeros.tipcalculator.composable.RoundTheTipRow

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TipCalculatorTheme {
                Scaffold() { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        TipCalculatorCard()
                    }
                }
            }
        }
    }
}

// 상태를 가지고 있는 Composable을 stateful composable 이라고 하고,
// 상태를 가지고 있지않은 Composable을 stateless composable 이라고 한다.
// 아래에서 TipCalculatorCard는 amountInput과 tipInput을 state로 가지고 있기 때문에 stateful composable
// EditNumberField는 state를 가지고 있지 않기 떄문에 stateless composable 이다.
// mutableStateOf : 변경 가능하며 compose에 의해 관찰되는 상태
// remember : recomposition 되어도 값을 유지함. -> 해당 값을 변경한 경우가 아니라면, 다른 이유로 recomposition해도 초기화하지 않고 이전 값을 가짐
// by : mutableState 객체의 값을 가져오기 위해서는 원래 .value를 사용해야 하나, by를 통해 이를 생략 가능
// [ex) val amount = amountInput.value.toDoubleOrNull() ?: 0.0 -> val amount = amountInput.toDoubleOrNull() ?: 0.0]
@Composable
fun TipCalculatorCard(){
    var amountInput by remember { mutableStateOf("") }
    val amount = amountInput.toDoubleOrNull() ?: 0.0    // amountInput 변경 시, 자동 재계산 되며 recompose
    var tipInput by remember { mutableStateOf("") }
    val tipPercent = tipInput.toDoubleOrNull() ?: 0.0   // tipInput 변경 시, 자동 재계산 되며 recompose
    var roundUp by remember { mutableStateOf(false) }
    val tip = calculateTip(amount,tipPercent,roundUp)

    Column(
        modifier = Modifier
            .statusBarsPadding()
            .verticalScroll(rememberScrollState()),     // 열을 세로로 스크롤 할 수 있음
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        Text(
            text="Calculate Tip",
            modifier = Modifier
                .align(alignment = Alignment.Start)
        )
        // 지불 금액 입력 TextField
        EditNumberField(
            label = R.string.bill_amount,
            leadingIcon = R.drawable.money,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, // 숫자만 입력 가능
                imeAction = ImeAction.Next          // 다음 TextField로 이동
            ),
            value = amountInput,
            onValueChange = {amountInput=it},
            modifier = Modifier
                .padding(bottom=30.dp)
                .fillMaxWidth()
        )
        // Tip Percentage 입력 TextField
        EditNumberField(
            label = R.string.how_was_the_service,
            leadingIcon = R.drawable.percent,
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number, // 숫자만 입력 가능
                imeAction = ImeAction.Done          // 사용자가 입력을 완료했음을 나타냄
            ),
            value = tipInput,
            onValueChange = {tipInput=it},
            modifier = Modifier
                .padding(bottom=30.dp)
                .fillMaxWidth()
        )
        RoundTheTipRow(
            roundUp = roundUp,
            onRoundUpChanged = { roundUp = it },
            modifier = Modifier.padding(bottom = 30.dp)
        )
        Text(
            text= stringResource(R.string.tip_amount,tip),
            style = MaterialTheme.typography.displaySmall
        )
        Spacer(modifier = Modifier.height(150.dp))
    }
}

// internal 키워드로 인해 메서드가 공개되지만(모듈 단위로 접근 제한, 컴파일 단위),
// @VisibleForTesting으로 인해 테스트 목적으로만 사용자에게 표시됨
@VisibleForTesting
internal fun calculateTip(
    amount: Double,
    tipPercent: Double = 15.0,
    roundUp: Boolean
): String {
    var tip = tipPercent / 100 * amount
    if(roundUp){
        tip = kotlin.math.ceil(tip)
    }
    return NumberFormat.getCurrencyInstance().format(tip)   // 현재 지역의 화폐 단위로 수를 반환
}

@Composable
fun EditNumberField(
    @StringRes label: Int,  // @StringRes : 매개변수가 반드시 문자열 리소스를 참조하여야 함
    @DrawableRes leadingIcon: Int,  // @DrawableRes : 매개변수가 반드시
    keyboardOptions: KeyboardOptions,
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier
){
    TextField(
        value = value,                      // 초기 입력 값
        leadingIcon = { Icon(painter = painterResource(id = leadingIcon),null) },   // 텍스트 입력란 선행 아이콘
        onValueChange = onValueChange,      // 데이터 변화시 수행할 메서드
        singleLine = true,                  // 한 줄로만 보이도록, 길면 좌우 스크롤
        label = {Text(stringResource(label))},  // Text Field에 입력해야 할 내용 설명
        keyboardOptions = keyboardOptions,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun TipCaculatorPreview(){
    TipCalculatorTheme {
        TipCalculatorCard()
    }
}