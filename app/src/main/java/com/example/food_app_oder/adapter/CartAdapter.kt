package com.example.food_app_oder.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContentProviderCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.login.MyApplication
import com.example.food_app_oder.model.CartItem
import com.example.food_app_oder.model.FoodCart
import com.google.android.material.card.MaterialCardView
import kotlinx.coroutines.NonDisposableHandle.parent

class CartAdapter(val context: Context, var data : List<CartItem>) : RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    lateinit var foodCart : FoodCart
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_food_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {


        val cartItem = data[position]
//        holder.productName.text = cartItem.productName
//        holder.productPrice.text = cartItem.productPrice.toString()
//        holder.productQuantity.text = cartItem.productQuantity.toString()
        holder.productCount.text = cartItem.quantity.toString()
        holder.productText.text = cartItem.name
        holder.productPrice.text = cartItem.price.toString()

//        holder.itemView.setOnClickListener {
//            onItemClick.onItemClick(position)
//        }
        holder.deleteBasketProduct.setOnClickListener {
            val sharedPreferences = context.getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
            val cartJson = sharedPreferences.getString("cartItem", "")
            foodCart = if (cartJson != null) {
                FoodCart.fromJson(cartJson)
            }
            else {
                FoodCart()
            }
            foodCart.removeFood(cartItem)
            data = foodCart.foods

            notifyDataSetChanged()
            holder.productPrice.text = cartItem.price.toString()
            val editor = sharedPreferences.edit()
            editor.putString("cartItem", foodCart.toJson())
            editor.apply()
        }


    }

//    fun removeFood(food: CartItem): List<CartItem> {
//        foods.remove(food)
//        return foods
//    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val productCount: TextView = itemView.findViewById(R.id.productCount)
        val productPrice: TextView = itemView.findViewById(R.id.productPrice)
        val productText: TextView = itemView.findViewById(R.id.productText)

        val deleteBasketProduct: MaterialCardView = itemView.findViewById(R.id.deleteBasketProduct)



    }
}