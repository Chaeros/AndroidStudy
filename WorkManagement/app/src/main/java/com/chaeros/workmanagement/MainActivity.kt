package com.chaeros.workmanagement

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chaeros.workmanagement.ui.theme.WorkManagementTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WorkManagementTheme {
                Scaffold() { innerPadding ->    // Scaffold가 TopBar, BottomBar 등의 공간을 피해 content가 잘 배치되도록 해주기 위해 innerPadding 사용
                    Surface(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding),
                        color = MaterialTheme.colorScheme.background    // 현재 테마(Light,Dark)에 따라 배경색 결정
                    ){
                        CompleteComposable(
                            title = stringResource(R.string.all_tasks_completed),
                            content = stringResource(R.string.nice_work)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun CompleteComposable(title: String, content: String, modifier: Modifier = Modifier ){
    val image = painterResource(R.drawable.ic_task_completed)
    Column(
        modifier = Modifier
            .fillMaxWidth()     // 부모 컨테이너의 너비를 모두 차지함
            .fillMaxHeight(),   // 부모 컨테이너의 높이를 모두 차지함
        verticalArrangement = Arrangement.Center,           // Column 내부 Composable들 수직으로 가운데 정렬
        horizontalAlignment = Alignment.CenterHorizontally  // Column 내부 Composable들 수평으로 가운데 정렬
    ) {
        Image(
            painter = image,
            contentDescription = "check image"
        )
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top=24.dp, bottom = 8.dp)
        )
        Text(
            text = content,
            fontSize = 16.sp
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    WorkManagementTheme {
        CompleteComposable(
            title = stringResource(R.string.all_tasks_completed),
            content = stringResource(R.string.nice_work)
        )
    }
}