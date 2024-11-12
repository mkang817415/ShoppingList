package com.example.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.shoppinglist.R


@Database(entities = [ShoppingItem::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase(){

    abstract fun shoppingDao(): ShoppingDAO

    companion object{
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase{
            //if the instance is not null return it, otherwise create new database
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, AppDatabase::class.java,
                    context.getString(R.string.shopping_database_db))
                    .fallbackToDestructiveMigration()
                    .build()
                    .also { Instance = it }
            }
        }
    }
}
