// SupermarketViewModelFactory.kt
package com.dom.lab8market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.dom.lab8market.data.dao.SupermarketItemDao

class SupermarketViewModelFactory(
    private val supermarketItemDao: SupermarketItemDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SupermarketViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SupermarketViewModel(supermarketItemDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
