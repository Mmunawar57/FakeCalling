package com.example.fakecalling.recyclerview

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.fakecalling.activity.CallActivity
import com.example.fakecalling.Admanager.AppNativeAds
import com.example.fakecalling.R
import com.example.fakecalling.RoomDatabase.history
import com.google.android.material.card.MaterialCardView

class DataAdapter(val context: Context, val list: List<Any>, val onClick: (history) -> Unit) :
    RecyclerView.Adapter<ViewHolder>() {
    private val adapterData = mutableListOf<history>()
    
    inner class DataAdapterViewHolder(itemView: View) : ViewHolder(itemView) {
        val clear = itemView.findViewById<ImageView>(R.id.history_dlt)
        val name_recycler = itemView.findViewById<TextView>(R.id.history_name)
        val number_recycler = itemView.findViewById<TextView>(R.id.history_number)
        val call = itemView.findViewById<ImageView>(R.id.history_call)
        
        @SuppressLint("NotifyDataSetChanged", "SuspiciousIndentation")
        fun bind(position: Int) {
            val dataModel = list[position] as history
            name_recycler.text = dataModel.name
            number_recycler.text = dataModel.number
            val context = itemView.context
            clear.setOnClickListener {
                onClick(dataModel)
                
            }
            call.setOnClickListener {
                
                val intent = Intent(context, CallActivity::class.java)
                intent.putExtra("name", dataModel.name)
                intent.putExtra("number", dataModel.number)
                context.startActivity(intent)
            }
        }
    }
    
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when (viewType) {
            TYPE_HISTORY -> DataAdapterViewHolder(
                LayoutInflater.from(
                    parent.context
                ).inflate(R.layout.recycler_datahistory, parent, false)
            )

            TYPE_ADD -> AddadapterViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.native_ad_container, parent, false)
            )

            else -> throw IllegalArgumentException("Invalid view type")
        }
        
        
    }
    
    
    //-----------onBindViewHolder: bind view with data model---------
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (list[position] is history) {
            (holder as DataAdapterViewHolder).bind(position)
        } else {
            (holder as AddadapterViewHolder).bind()
        }
        
    }
    
    override fun getItemCount(): Int = list.size
    
    override fun getItemViewType(position: Int): Int {
        return when (list[position]) {
            is history -> TYPE_HISTORY
            else -> TYPE_ADD
            
        }
    }
    
    fun deleteItem(index: Int) {
        adapterData.removeAt(index)
        notifyDataSetChanged()
    }
    
    fun setData(data: List<history>) {
        adapterData.apply {
            clear()
            addAll(data)
        }
    }
    
    companion object {
        private const val TYPE_HISTORY = 0
        private const val TYPE_ADD = 1
    }
    
    
    inner class AddadapterViewHolder(itemView: View) : ViewHolder(itemView) {
        val adHolder = itemView.findViewById<FrameLayout>(R.id.adContainer)
        val adParent = itemView.findViewById<MaterialCardView>(R.id.parent)
        
        
        fun bind() {
            AppNativeAds.inflateBigAds(context, adHolder)
        }
        
    }
}