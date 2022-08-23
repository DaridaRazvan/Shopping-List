package com.example.shoppinglist.data

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class ProductRepository(private val productDao: ProductDao) {

    val readAllData: LiveData<List<Product>> = productDao.readAllData()

    fun filterData(query: String): Flow<List<Product>> = productDao.filterData(query)

    suspend fun addProduct(product: Product){
        productDao.addProduct(product)
    }

    suspend fun updateProduct(product: Product){
        productDao.updateProduct(product)
    }

    suspend fun deleteProduct(product: Product){
        productDao.deleteProduct(product)
    }

    suspend fun deleteCheckedProducts(){
        productDao.deleteCheckedProducts()
    }
}