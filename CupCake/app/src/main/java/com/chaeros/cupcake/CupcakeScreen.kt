package com.chaeros.cupcake

import android.content.Context
import android.content.Intent
import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.chaeros.cupcake.ui.OrderViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.chaeros.cupcake.data.DataSource
import com.chaeros.cupcake.ui.OrderSummaryScreen
import com.chaeros.cupcake.ui.SelectOptionScreen
import com.chaeros.cupcake.ui.StartOrderScreen

enum class CupcakeScreen(@StringRes val title: Int) {
    Start(title=R.string.app_name),
    Flavor(title=R.string.choose_flavor),
    Pickup(title=R.string.choose_pickup_date),
    Summary(title=R.string.order_summary)
}

// TopAppBar와 TopAppBarDefaults.mediumTopAppBarColors()는 Material3의 실험적(Experimental) API로 아래 명시적 OptIn 설정이 필요
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CupcakeAppBar(
    currentScreen: CupcakeScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

// rememberNavController : Compose 환경에서 탐색을 관리하는 NavController 인스턴스를 생성하고 기억하는 역할 수행
@Composable
fun CupcakeApp(
    viewModel: OrderViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    // 현재 네비게이션 백 스택의 맨 위 항목(즉, 현재 화면)에 대한 NavBackStackEntry를 상태로 관찰
    //화면(route)이 바뀔 때마다 자동으로 업데이트
    val backStackEntry by navController.currentBackStackEntryAsState()
    // 현재 표시되고 있는 화면의 route 이름 호출
    // 문자열로 받은 "Flavor" 같은 값을 열거형(Enum) 값으로 변환 (CupcakeScreen.Flavor.name가 곧 enum에서 Flavor 였으므로)
    val currentScreen = CupcakeScreen.valueOf(
        backStackEntry?.destination?.route ?: CupcakeScreen.Start.name
    )
    Scaffold(
        topBar = {
            CupcakeAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() } // navigateUp() : 현재 화면을 종료하고, 백 스택에 있는 이전 화면으로 이동
            )
        }
    ) { innerPadding ->
        val uiState by viewModel.uiState.collectAsState()

        NavHost(
            // NavController를 연결하여 탐색 상태를 관리
            navController = navController,
            // 앱이 시작될 때 표시할 첫 화면 지정 (StartOrderScreen)
            startDestination = CupcakeScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = CupcakeScreen.Start.name) {
                StartOrderScreen(
                    quantityOptions = DataSource.quantityOptions,
                    onNextButtonClicked = {
                        viewModel.setQuantity(it)
                        navController.navigate(CupcakeScreen.Flavor.name)
                    },
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(dimensionResource(R.dimen.padding_medium))
                )
            }

            composable(route = CupcakeScreen.Flavor.name) {
                // 현재 Context 객체 가져오기 (리소스 문자열 변환용)
                val context = LocalContext.current
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Pickup.name)},
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = DataSource.flavors.map { id -> context.resources.getString(id) },
                    onSelectionChanged = { viewModel.setFlavor(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Pickup.name) {
                SelectOptionScreen(
                    subtotal = uiState.price,
                    onNextButtonClicked = { navController.navigate(CupcakeScreen.Summary.name) },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    options = uiState.pickupOptions,
                    onSelectionChanged = { viewModel.setDate(it) },
                    modifier = Modifier.fillMaxHeight()
                )
            }

            composable(route = CupcakeScreen.Summary.name) {
                // Jetpack Compose에서는 전통적인 Android의 Context 객체에 직접 접근하지 않기 때문에,
                // Compose 컴포저블 내에서 현재의 Context를 가져오려면 LocalContext.current 를 사용
                // Android에서 Intent, Toast, Resources, startActivity() 등 많은 기능은 Context를 필요
                val context = LocalContext.current
                OrderSummaryScreen(
                    orderUiState = uiState,
                    onSendButtonClicked = {subject:String, summary:String->
                        shareOrder(context, subject = subject, summary = summary)
                    },
                    onCancelButtonClicked = {
                        cancelOrderAndNavigateToStart(viewModel, navController)
                    },
                    modifier = Modifier.fillMaxHeight()
                )
            }
        }
    }
}

private fun cancelOrderAndNavigateToStart(
    viewModel: OrderViewModel,
    navController: NavHostController
) {
    viewModel.resetOrder()
    navController.popBackStack(CupcakeScreen.Start.name, inclusive = false)
}

// 주문 요약 내용을 다른 앱으로 공유할 수 있도록 하는 기능 수행
private fun shareOrder(context: Context, subject: String, summary: String) {
     val intent = Intent(Intent.ACTION_SEND).apply {
         type = "text/plain"                        // 인텐트와 함께 전송되는 추가 데이터 유형 지정
         putExtra(Intent.EXTRA_SUBJECT, subject)    // EXTRA_SUBJECT의 제목 전달
     }
    context.startActivity(
        Intent.createChooser(
            intent,
            context.getString(R.string.new_cupcake_order)
        )
    )
}