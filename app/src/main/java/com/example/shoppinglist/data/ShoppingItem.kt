package com.example.shoppinglist.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.shoppinglist.R


@Entity(tableName = "shoppingTable")
data class ShoppingItem(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "category") val category: ShoppingCategory,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "description") val description: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "status") var status: Boolean
)


enum class ShoppingCategory {
    FOOD, TECH, BOOK, DRINK, MEDICINE, OTHER;

    override fun toString(): String {
        return when (this) {
            FOOD -> "Food"
            TECH -> "Tech"
            BOOK -> "Book"
            DRINK -> "Drink"
            MEDICINE -> "Medicine"
            OTHER -> "Other"
        }
    }

    fun getIcon(): Int{
        return if (this == ShoppingCategory.FOOD){
            R.drawable.food
        } else if (this == ShoppingCategory.TECH){
            R.drawable.tech
        } else if (this == ShoppingCategory.BOOK){
            R.drawable.book
        } else if (this == ShoppingCategory.DRINK){
            R.drawable.drink
        } else if (this == ShoppingCategory.MEDICINE){
            R.drawable.medicine
        } else {
            R.drawable.other
        }
    }
}



