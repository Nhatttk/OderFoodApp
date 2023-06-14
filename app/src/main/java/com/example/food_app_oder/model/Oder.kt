package com.example.food_app_oder.model

import com.google.gson.annotations.SerializedName

class Oder (

    @SerializedName("id_oder")
    val id_oder: Int,
    @SerializedName("ghichu")
    val ghichu: String,
    @SerializedName("diachi")
    val diachi: String,
    @SerializedName("danhsachsp")
    val danhsachsp: String,
    @SerializedName("tongtien")
    val tongtien: Int,
    @SerializedName("email")
    val email: String

    )