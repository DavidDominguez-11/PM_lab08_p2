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

    fun addItem(itemName: String, quantity: String, imagePath: String?) {
        // Convertir la cantidad de String a Int, con manejo de errores
        val quantityInt = try {
            quantity.toInt()
        } catch (e: NumberFormatException) {
            0 // Valor por defecto si la conversión falla
        }

        val item = SupermarketItem(
            itemName = itemName,
            quantity = quantityInt,  // Ahora pasamos un Int
            imagePath = imagePath
        )

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