package com.example.food_app_oder.model

import com.google.gson.annotations.SerializedName

class User(

    @SerializedName("email")
    var email: String,
    @SerializedName("phone_number")
    var phone: String,
    @SerializedName("password")
    var password: String

        )