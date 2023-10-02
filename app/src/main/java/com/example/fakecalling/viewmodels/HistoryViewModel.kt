package com.example.fakecalling.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.fakecalling.RoomDatabase.HistoryDatabases
import com.example.fakecalling.RoomDatabase.history
import com.example.fakecalling.recyclerview.DataModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class HistoryViewModel(application: Application):AndroidViewModel(application) {

    val list =MutableLiveData<MutableList<history>>()
    val data=HistoryDatabases.getDatabase(application.applicationContext)

    fun getHistoryData()= CoroutineScope(Dispatchers.Default).launch {
        data.historyDao().gethistory().collect{item->
            list.postValue(item as MutableList)
        }


    }

    fun delete(history: history)= viewModelScope.launch{
        data.historyDao().deletehistory(history)
    }
}