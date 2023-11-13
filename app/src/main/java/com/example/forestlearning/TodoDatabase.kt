package com.example.forestlearning

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [Todo::class], version = 1, exportSchema = false)
abstract class TodoDatabase : RoomDatabase() {
    abstract fun todoDao() : TodoDao

    companion object{
        @Volatile
        private var INSTANCE : TodoDatabase? = null

        fun getDatabase(context : Context) : TodoDatabase? {
            if(INSTANCE == null){
                synchronized(TodoDatabase::class){
                    INSTANCE = databaseBuilder(
                        context.applicationContext,
                        TodoDatabase::class.java,
                        "todo_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }
}