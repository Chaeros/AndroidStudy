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

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            ItemEditViewModel(
                this.createSavedStateHandle()
            )
        }

        initializer {
            ItemEntryViewModel()
        }

        initializer {
            ItemDetailsViewModel(
                this.createSavedStateHandle()
            )
        }

        initializer {
            HomeViewModel()
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [InventoryApplication].
 */
fun CreationExtras.inventoryApplication(): InventoryApplication =
    (this[AndroidViewModelFactory.APPLICATION_KEY] as InventoryApplication)
