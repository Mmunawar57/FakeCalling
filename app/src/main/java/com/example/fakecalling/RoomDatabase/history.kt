package com.example.fakecalling.RoomDatabase

import androidx.room.Entity
import androidx.room.PrimaryKey
@Entity(tableName = "history")
data class history(


    val name:String,
    val number:String

){
    @PrimaryKey(autoGenerate = true)
    var id=0
}
