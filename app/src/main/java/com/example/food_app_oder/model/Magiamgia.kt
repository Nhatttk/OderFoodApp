package com.example.food_app_oder.model

import com.google.gson.annotations.SerializedName

class Magiamgia(

    @SerializedName("id_magiamgia")
    val id_magiamgia: Int,
    @SerializedName("magiamgia")
    val magiamgia: String,
    @SerializedName("sotiengiam")
    val sotiengiam: Int

)