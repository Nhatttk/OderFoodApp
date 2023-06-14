package com.example.food_app_oder.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class CartItem (

        @SerializedName("id")
        val id: Int,
        @SerializedName("nameFood")
        val name: String,
        @SerializedName("price")
        val price: Int,
        var quantity : Int
        )