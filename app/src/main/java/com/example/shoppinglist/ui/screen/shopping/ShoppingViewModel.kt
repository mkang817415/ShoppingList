package com.example.shoppinglist.ui.screen.shopping

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.shoppinglist.R
import com.example.shoppinglist.data.ShoppingDAO
import com.example.shoppinglist.data.ShoppingItem
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    val shoppingDAO: ShoppingDAO
) : ViewModel() {


    fun getAllItems(): Flow<List<ShoppingItem>> {
        return shoppingDAO.getAllItems()
    }

    fun addItem(shoppingItem: ShoppingItem){
        viewModelScope.launch(Dispatchers.IO){
            shoppingDAO.insert(shoppingItem)
        }
    }

    fun removeItem(shoppingItem: ShoppingItem){
        viewModelScope.launch(Dispatchers.IO){
            shoppingDAO.delete(shoppingItem)
        }
    }

    fun editItem(editedItem: ShoppingItem){
        viewModelScope.launch(Dispatchers.IO){
            shoppingDAO.update(editedItem)
        }
    }

    fun changeStatus(shoppingItem: ShoppingItem, value: Boolean){
        val updatedTodo = shoppingItem.copy()
        updatedTodo.status = value
        viewModelScope.launch(Dispatchers.IO){
            shoppingDAO.update(updatedTodo)
        }
    }

    fun getItemsByCategory(category: String): Flow<List<ShoppingItem>> {
        return shoppingDAO.getItemsByCategory(category)
    }

    fun getItemsCountByCategory(category: String): Flow<Int> {
        return shoppingDAO.getItemsCountByCategory(category)
    }

    fun getTotalPrice(): Flow<Int> {
        return shoppingDAO.getTotalPrice()
    }

    fun clearAll(){
        viewModelScope.launch(Dispatchers.IO){
            shoppingDAO.deleteAllItems()
        }
    }




}




