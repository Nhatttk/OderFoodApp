package com.example.food_app_oder.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Food (

        @SerializedName("idFood")
        var id: Int,
        @SerializedName("nameFood")
        var name: String,
        @SerializedName("price")
        var price: Int,
        @SerializedName("decription")
        var description: String,
        @SerializedName("imageR")
        var imageUrl: String,
        @SerializedName("id_type_food")
        var typeId: Int,


        )