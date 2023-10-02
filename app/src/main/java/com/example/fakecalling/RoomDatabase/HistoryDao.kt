package com.example.fakecalling.RoomDatabase

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface HistoryDao {
    @Insert
    suspend fun inserthistory(
        history: history
    )
    @Update
    suspend fun updatehistory(history: history)

    @Delete
    suspend fun deletehistory(history: history)

    @Query("SELECT *FROM history")
    fun gethistory(): Flow<List<history>>
}