package com.example.fakecalling.recyclerview

sealed class DataModel {

    data class history(
        val profile: Int,
        val call:Int,
        val name: String,
        val number: String,
        val clear: Int
    ) : DataModel()


    data class add (
       val ad_text:String
    ) : DataModel()
}