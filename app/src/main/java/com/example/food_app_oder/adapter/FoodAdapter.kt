package com.example.food_app_oder.adapter

import android.content.Context
import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.getSystemService
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app_oder.R
import com.example.food_app_oder.model.Food

class FoodAdapter(val context: Context, var data : List<Food>, val onItemClick : OnItemClickListener) : RecyclerView.Adapter<FoodAdapter.ViewHolder>() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_food_details, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
       return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val foodDetails = data[position]
        holder.foodND.text = foodDetails.name
        holder.foodPD.text = foodDetails.price.toString()
        holder.imageFD?.let {
            Glide.with(context)
                .load(foodDetails.imageUrl)
                .into(it)
        }

        holder.itemView.setOnClickListener {
            onItemClick.onItemClick(position)
        }
    }

    fun setFilterList(mList : List<Food>) {
        data = mList
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
            val imageFD : ImageView = itemView.findViewById(R.id.imgFD)
            val foodND : TextView = itemView.findViewById(R.id.txtFN)
            val foodPD : TextView = itemView.findViewById(R.id.txtFP)
    }
}