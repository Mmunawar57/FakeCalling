package com.example.fakecalling.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakecalling.R
import com.example.fakecalling.viewmodels.Slider

class SliderAdapter(val list: List<Slider>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class SliderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val ivWalpaper = itemView.findViewById<ImageView>(R.id.img_Slider)
        val txt = itemView.findViewById<TextView>(R.id.discription)
        fun onBind(position: Int) {

            txt.text = list[position].disp
            Glide.with(ivWalpaper)
                .load(list[position].img)
                .into(ivWalpaper)
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return SliderViewHolder(
            LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        (holder as SliderViewHolder).onBind(position)

    }

    override fun getItemCount(): Int {
        return list.size
    }
}
