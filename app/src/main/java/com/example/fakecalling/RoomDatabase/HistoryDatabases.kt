package com.example.fakecalling.RoomDatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
@Database(entities = [history::class], version = 1)
abstract class HistoryDatabases:RoomDatabase() {
    abstract fun historyDao():HistoryDao
    companion object{
        @Volatile
        private var INSTANCE:HistoryDatabases? = null
        fun getDatabase (context: Context):HistoryDatabases{
            if (INSTANCE==null){
                synchronized(this){
                    INSTANCE= Room.databaseBuilder(context.applicationContext,HistoryDatabases::class.java,
                        "historyDb").build()
                }

            }
            return INSTANCE!!
        }
    }
}