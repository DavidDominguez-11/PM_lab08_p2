// SupermarketViewModel.kt
package com.dom.lab8market

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dom.lab8market.data.dao.SupermarketItemDao
import com.dom.lab8market.data.model.SupermarketItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SupermarketViewModel(private val supermarketItemDao: SupermarketItemDao) : ViewModel() {

    val items: Flow<List<SupermarketItem>> = supermarketItemDao.getAllItems()

    fun addItem(itemName: String, quantity: Int, imagePath: String?) {
        val item = SupermarketItem(itemName = itemName, quantity = quantity, imagePath = imagePath)
        viewModelScope.launch {
            supermarketItemDao.insertItem(item)
        }
    }

    fun deleteItem(item: SupermarketItem) {
        viewModelScope.launch {
            supermarketItemDao.deleteItem(item)
        }
    }
}
