package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("DELETE FROM product WHERE isChecked = 1")
    suspend fun deleteCheckedProducts()

    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%'")
    fun filterData(query : String) : Flow<List<Product>>

    @Query("SELECT * FROM product")
    fun readAllData(): LiveData<List<Product>>
}