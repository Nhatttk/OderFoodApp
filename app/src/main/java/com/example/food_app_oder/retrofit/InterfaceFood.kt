package com.example.food_app_oder.retrofit

import TypeFood
import com.example.food_app_oder.admin.model.ResponLogin
import com.example.food_app_oder.admin.model.ResponMagiamgia
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.model.Oder
//import com.example.food_app_oder.model.TypeFood
import com.example.food_app_oder.model.User
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface InterfaceFood {
    @GET("getDataTypeFood.php")
    fun getTypeFood(): Call<List<TypeFood>>

    @FormUrlEncoded
    @POST("getDataFoodDetails.php")
    fun getFood(
        @Field("id_type_food")
        id: Int?
    ) : Call<List<Food>>

    @GET("getData.php")
    fun getSearch() : Call<List<Food>>

    @FormUrlEncoded
    @POST("insertUser.php")
    fun insertUser(
        @Field("email")
        email: String?,
        @Field("phone_number")
        phone: String?,
        @Field("password")
        pass: String?
    ) : Call<List<User>>

    @FormUrlEncoded
    @POST("login.php")
    fun login(
        @Field("email")
        email: String?,
        @Field("password")
        password: String?,
    ) : Call<ResponLogin>

    @FormUrlEncoded
    @POST("insertFood.php")
    fun insertFood(
        @Field("nameFood")
        nameFood: String?,
        @Field("price")
        price: String?,
        @Field("decription")
        decription: String?,
        @Field("imageR")
        imageR: String?,
        @Field("id_type_food")
        id_type_food: Int?,
    ) : Call<ResponseBody>


    @FormUrlEncoded
    @POST("insertOder.php")
    fun insertOder(
        @Field("ghichu")
        ghichu : String?,
        @Field("diachi")
        diachi: String?,
        @Field("danhsachsp")
        danhsachsp: String?,
        @Field("tongtien")
        tongtien: Int?,
        @Field("email")
        email : String?,
    ) : Call<List<Oder>>

    @FormUrlEncoded
    @POST("magiamgia.php")
    fun magiamgia(
        @Field("magiamgia")
        magiamgia: String?

    ) : Call<ResponMagiamgia>


    @GET("thongtindathang.php")
    fun thongtindathang(): Call<List<Oder>>

    @FormUrlEncoded
    @POST("lichsudathang.php")
    fun lichsudathang(
        @Field("email")
        email: String?
    ) : Call<List<Oder>>
}