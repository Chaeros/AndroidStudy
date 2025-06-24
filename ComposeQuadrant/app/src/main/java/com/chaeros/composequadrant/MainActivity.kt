package com.chaeros.composequadrant

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chaeros.composequadrant.ui.theme.ComposeQuadrantTheme
import com.chaeros.composequadrant.ui.theme.FirstQuadrantColor
import com.chaeros.composequadrant.ui.theme.FourthQuadrantColor
import com.chaeros.composequadrant.ui.theme.SecondQuadrantColor
import com.chaeros.composequadrant.ui.theme.ThirdQuadrantColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ComposeQuadrantTheme {
                Scaffold() { innerPadding ->
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        DrawLayout()
                    }
                }
            }
        }
    }
}

@Composable
fun DrawLayout(){
    Column(Modifier.fillMaxWidth()) // 하나의 행을 화면 전체 너비로 설정
    {
        // Modifier.weight는 레이아웃 공간을 나누는 비율 단위
        // Column 내 2개의 Row가 서로 같은 1f, 1f를 가지므로 1:1 비율을 가짐
        // 만약 하나의 Row에서 weight를 2로 가지면 해당 Row가 2배의 높이를 가짐
        Row(Modifier.weight(1f)){
            Quadrant(
                title = stringResource(R.string.first_quadrant_title),
                content = stringResource(R.string.first_quadrant_content),
                color = FirstQuadrantColor,
                // 하나의 열 내에서도 레이아웃 비율이 1f, 1f이므로 1:1로 가짐
                modifier = Modifier.weight(1f)
            )
            Quadrant(
                title = stringResource(R.string.second_quadrant_title),
                content = stringResource(R.string.second_quadrant_content),
                color = SecondQuadrantColor,
                modifier = Modifier.weight(1f)
            )
        }
        Row(Modifier.weight(1f)){
            Quadrant(
                title = stringResource(R.string.third_quadrant_title),
                content = stringResource(R.string.third_quadrant_content),
                color = ThirdQuadrantColor,
                modifier = Modifier.weight(1f)
            )
            Quadrant(
                title = stringResource(R.string.fourth_quadrant_title),
                content = stringResource(R.string.fourth_quadrant_content),
                color =FourthQuadrantColor,
                modifier = Modifier.weight(1f)
            )
        }
    }
}

@Composable
fun Quadrant(
    title: String,
    content: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .background(color)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom=16.dp)
        )
        Text(
            text = content,
            textAlign = TextAlign.Justify
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ComposeQuadrantTheme {
        DrawLayout()
    }
}