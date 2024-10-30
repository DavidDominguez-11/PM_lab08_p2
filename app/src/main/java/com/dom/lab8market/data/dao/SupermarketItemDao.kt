package com.dom.lab8market.data.dao

import androidx.room.*
import com.dom.lab8market.data.model.SupermarketItem
import kotlinx.coroutines.flow.Flow

@Dao
interface SupermarketItemDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(item: SupermarketItem)

    @Update
    suspend fun updateItem(item: SupermarketItem)

    @Delete
    suspend fun deleteItem(item: SupermarketItem)

    @Query("SELECT * FROM supermarket_items ORDER BY id ASC")
    fun getAllItems(): Flow<List<SupermarketItem>>
}
