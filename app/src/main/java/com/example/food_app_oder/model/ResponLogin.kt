package com.example.food_app_oder.admin.model

import com.example.food_app_oder.model.User
import com.google.gson.annotations.SerializedName

data class ResponLogin(
    @SerializedName("success") val success: Boolean,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<User>,
)
