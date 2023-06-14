package com.example.food_app_oder.admin.model

import com.google.gson.annotations.SerializedName

data class TypeFoodResponse(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String
)
