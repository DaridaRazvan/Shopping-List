package com.example.shoppinglist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Product::class], version = 1, exportSchema = false)
abstract class ShoppingListDatabase : RoomDatabase() {

    abstract fun productDao(): ProductDao

    companion object{
        @Volatile
        private var INSTANCE: ShoppingListDatabase? = null

        fun getDatabase(context: Context): ShoppingListDatabase{
            val tempInstance = INSTANCE
            if(tempInstance!= null) {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingListDatabase::class.java,
                    "shopping_list"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}