package com.example.shoppinglist.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingDAO{

    @Query("SELECT * FROM shoppingTable")
    fun getAllItems() : Flow<List<ShoppingItem>>

    @Query("SELECT * FROM shoppingTable WHERE id = :id")
    fun getItem(id: Int): Flow<ShoppingItem>

    @Query("SELECT COUNT(*) from shoppingTable")
    suspend fun getItemsCount(): Int

    @Query("SELECT * FROM shoppingTable WHERE LOWER(category) = LOWER(:category)")
    fun getItemsByCategory(category: String): Flow<List<ShoppingItem>>

    @Query("SELECT COUNT(*) from shoppingTable WHERE LOWER(category) = LOWER(:category)")
    fun getItemsCountByCategory(category: String): Flow<Int>

    @Query("SELECT COALESCE(SUM(price), 0) FROM shoppingTable")
    fun getTotalPrice(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(shopping: ShoppingItem)

    @Update
    suspend fun update(shopping: ShoppingItem)

    @Delete
    suspend fun delete(shopping: ShoppingItem)

    @Query("DELETE FROM shoppingTable")
    suspend fun deleteAllItems()


}