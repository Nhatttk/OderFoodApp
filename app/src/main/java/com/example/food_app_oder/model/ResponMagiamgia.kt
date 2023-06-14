package com.example.food_app_oder.admin.model

import com.example.food_app_oder.model.Magiamgia
import com.example.food_app_oder.model.Oder
import com.google.gson.annotations.SerializedName

data class ResponMagiamgia(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Magiamgia>,
)
