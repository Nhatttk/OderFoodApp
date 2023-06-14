package com.example.food_app_oder.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.activity.Payment
import com.example.food_app_oder.adapter.CartAdapter
import com.example.food_app_oder.adapter.OnItemClickListener
import com.example.food_app_oder.login.MyApplication
import com.example.food_app_oder.model.CartItem
import com.example.food_app_oder.model.FoodCart
import com.example.food_app_oder.untils.Untils
import com.google.android.material.card.MaterialCardView
import com.google.gson.Gson


class CartFragment : Fragment() {

    lateinit var adapter : CartAdapter
     lateinit var foodCart : FoodCart

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Kiểm tra nội dung của arrCart bằng Log
//        Log.d("CartFragment", "arrCart: ${foodCart}")

        val totalAmount: TextView = view.findViewById(R.id.totalAmount)
        val cartRecyclerView : RecyclerView = view.findViewById(R.id.cartRecyclerView)
        val approveBasket : AppCompatButton = view.findViewById(R.id.approveBasket)



        loadFoodCartFromSharedPreferences()

            adapter = CartAdapter(requireActivity(),foodCart.foods.toList())
//            Log.d("CartFragment", "arrCart: $cartJson")
//            Log.d("foodCart", "foodCart: ${foodCart.foods}")

        var totalAmountCount = 0
        for (i in foodCart.foods.toList()) {
            totalAmountCount += i.price*i.quantity
        }
        totalAmount.text = totalAmountCount.toString()

            cartRecyclerView.adapter = adapter
            cartRecyclerView.layoutManager = LinearLayoutManager(
            requireActivity(), LinearLayoutManager.VERTICAL, false
        )


        // đặt hàng
        approveBasket.setOnClickListener {
            val intent = Intent(requireActivity(), Payment::class.java)
            intent.putExtra("listCart", foodCart.toJson())
            intent.putExtra("totalAmount", totalAmountCount)
            startActivity(intent)
        }
    }





    private fun loadFoodCartFromSharedPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
        val cartJson = sharedPreferences.getString("cartItem", "")
        foodCart = if (cartJson != null) {
            FoodCart.fromJson(cartJson)
        }
        else {
            FoodCart()
        }
    }
    private fun saveFoodCartToSharedPreferences() {
        val sharedPreferences = requireActivity().getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("cartItem", foodCart.toJson())
        editor.apply()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cart, container, false)
    }

}