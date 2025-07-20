package com.chaeros.dessertclicker.ui.composable

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.core.content.ContextCompat
import com.chaeros.dessertclicker.R
import com.chaeros.dessertclicker.model.Dessert

@Composable
fun DessertClickerApp(
    desserts: List<Dessert>
) {
    // rememberSaveable도 remember와 같이 상태 값을 저장하는 역할을 수행한다.
    // 단, 다른 점이 있다면 Activity가 destroy 되면 remember는 상태를 Compose 내부 메모리에 임시 저장했기에 상태를 잃는 반면,
    // rememberSaveable은 destroy 되었을 때도 Android 시스템 Bundle에 상태를 저장하기 때문에 상태를 잃지 않는다.
    // 단, 안드로이드 앱 자체를 껏다 켰을 경우에는 remeberSaveable 상태도 값을 잃기에 이 때는 viewModel을 사용해야 한다.
    var revenue by rememberSaveable { mutableStateOf(0) }
    var dessertsSold by rememberSaveable { mutableStateOf(0) }

    val currentDessertIndex by rememberSaveable { mutableStateOf(0) }

    var currentDessertPrice by rememberSaveable {
        mutableStateOf(desserts[currentDessertIndex].price)
    }
    var currentDessertImageId by rememberSaveable {
        mutableStateOf(desserts[currentDessertIndex].imageId)
    }

    Scaffold(
        // topBar : Scaffold Composable의 상단 영역 정의
        // bottomBar도 생성 가능
        topBar = {
            // Context : Android에서 앱 환경(Activity, Application 등 모두 포함 가능)에 대한 정보와 기능을 담은 객체
            val intentContext = LocalContext.current            // 현재 Compose 트리에서 사용 가능한 Android Context 객체를 가져오는 코드
            val layoutDirection = LocalLayoutDirection.current  // 현재 UI의 텍스트 방향(왼쪽 → 오른쪽인지, 오른쪽 → 왼쪽인지)
            DessertClickerAppBar(
                onShareButtonClicked = {
                    shareSoldDessertsInformation(
                        intentContext = intentContext,
                        dessertsSold = dessertsSold,
                        revenue = revenue
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        // 디바이스의 노치나 내비게이션 바를 피해서,
                        // 그리고 언어 방향성도 맞춰서 좌우에 안전한 여백 생성
                        start = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateStartPadding(layoutDirection),
                        end = WindowInsets.safeDrawing.asPaddingValues()
                            .calculateEndPadding(layoutDirection),
                    )
                    .background(MaterialTheme.colorScheme.primary)
            )
        }
    ) { contentPadding ->
        DessertClickerScreen(
            revenue = revenue,
            dessertsSold = dessertsSold,
            dessertImageId = currentDessertImageId,
            onDessertClicked = {
                // Update the revenue
                revenue += currentDessertPrice
                dessertsSold++

                // Show the next dessert
                val dessertToShow = determineDessertToShow(desserts, dessertsSold)
                currentDessertImageId = dessertToShow.imageId
                currentDessertPrice = dessertToShow.price
            },
            modifier = Modifier.padding(contentPadding)
        )
    }
}

private fun shareSoldDessertsInformation(intentContext: Context, dessertsSold: Int, revenue: Int) {
    // Intent 객체를 생성하고 String 값을 담음
    val sendIntent = Intent().apply {
        action = Intent.ACTION_SEND     // 다른 앱과의 공유할 것임을 의미
        putExtra(                       // 공유할 텍스트 추가
            Intent.EXTRA_TEXT,
            intentContext.getString(R.string.share_text, dessertsSold, revenue)
        )
        type = "text/plain"             // 텍스트 데이터임을 명시
    }

    // Intent.createChooser로 Intent객체를 감쌈으로서
    // 사용자가 공유할 앱을 선택할 수 있는 다이얼로그 UI가 뜬다거나
    // 공유할 내용을 확인해 볼 수 있다거나 등, 기기가 달라도 시스템이 안정적이고 일관되도록 할 수 있음
    val shareIntent = Intent.createChooser(sendIntent, null)

    try {
        // 시스템 공유하겠다는 요청을 통해, 실제 공유할 Intent 객체 실행하여 공유 UI 창이 띄워짐
        ContextCompat.startActivity(intentContext, shareIntent, null)
        //intentContext.startActivity(sendIntent)
        Toast.makeText(
            intentContext,
            "누르기 성공요",
            Toast.LENGTH_LONG
        ).show()
    } catch (e: ActivityNotFoundException) {
        // 공유 가능한 앱이 존재하지 않는다면 Toast로 안내 메시지 표시
        // Toast : 사용자 기기 하단에 잠깐 띄웠다가 사라지는 짧은 메시지 알림 UI
        // Toast({어떤 화면/앱 context에서 Toast를 띄울 건지},{보여줄 메시지},{얼마나 오래 보여줄지})
        // .show() : 실제로 Toast를 화면에 띄움
        Toast.makeText(
            intentContext,
            intentContext.getString(R.string.sharing_not_available),
            Toast.LENGTH_LONG
        ).show()
    }
}

fun determineDessertToShow(
    desserts: List<Dessert>,
    dessertsSold: Int
): Dessert {
    var dessertToShow = desserts.first()
    for (dessert in desserts) {
        if (dessertsSold >= dessert.startProductionAmount) {
            dessertToShow = dessert
        } else {
            break
        }
    }
    return dessertToShow
}