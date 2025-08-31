package com.chaeros.inventory.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.chaeros.inventory.InventoryApplication
import com.chaeros.inventory.ui.home.HomeViewModel
import com.chaeros.inventory.ui.item.ItemDetailsViewModel
import com.chaeros.inventory.ui.item.ItemEditViewModel
import com.chaeros.inventory.ui.item.ItemEntryViewModel

// viewModel 생성해주는 Factory
object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                // SavedStateHandle이란 안드로이드 Jetpack에서 제공하는 상태 저장 도우미 객체
                // ViewModel 안에서 UI 상태나 매개변수를 저장하고 복원할 수 있게 해줌
                // ex) 프로세스가 죽었다가 다시 살아날 때, 화면 회전 같은 구성 변경 등
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            ItemEntryViewModel(inventoryApplication().container.itemsRepository)
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle(),
                inventoryApplication().container.itemsRepository
            )
        }

        initializer {
            HomeViewModel(inventoryApplication().container.itemsRepository)
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
