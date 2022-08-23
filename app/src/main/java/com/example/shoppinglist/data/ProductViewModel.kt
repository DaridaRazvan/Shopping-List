package com.example.shoppinglist.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch

class ProductViewModel(application: Application) : AndroidViewModel(application) {

    //val readAllData: LiveData<List<Product>>
    private val repository: ProductRepository

    init{
        val productDao = ShoppingListDatabase.getDatabase(application).productDao()
        repository = ProductRepository(productDao)
        //readAllData = repository.readAllData
    }

    val searchQuery = MutableStateFlow("")
    private val productFlow = searchQuery.flatMapLatest {
        repository.filterData(it)
    }

    val products = productFlow.asLiveData()

//    fun filterData(query : String): LiveData<List<Product>> {
//        return repository.filterData(query)
//    }

    fun addProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO){
            repository.addProduct(product)
        }
    }

    fun updateProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateProduct(product)
        }
    }

    fun deleteProduct(product: Product){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteProduct(product)
        }
    }

    fun deleteCheckedProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCheckedProducts()
        }
    }


}