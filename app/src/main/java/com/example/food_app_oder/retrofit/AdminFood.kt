package com.example.food_app_oder.retrofit

import TypeFood
import com.example.food_app_oder.admin.model.TypeFoodResponse
import com.example.food_app_oder.admin.model.SelectedFoodType
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.model.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

interface AdminFood {
    @GET("getNameTypeFood.php")
    fun getNameTypeFood(): Call<List<SelectedFoodType>>
    @GET("getData.php")
    fun getFood(): Call<List<Food>>

    @GET("getDataUser.php")
    fun getDataUser(): Call<List<User>>

    @FormUrlEncoded
    @POST("deleteFood.php")
    fun deleteFood(
        @Field("idFood")
        idFood: Int?
    ) : Call<ResponseBody>

    @FormUrlEncoded
    @POST("deleteUser.php")
    fun deleteUser(
        @Field("email")
        email: String?
    ) : Call<TypeFoodResponse>


    @FormUrlEncoded
    @POST("deleteTypeFood.php")
    fun deleteTypeFood(
        @Field("id_type_food")
        id_type_food: Int?
    ) : Call<TypeFoodResponse>

    @FormUrlEncoded
    @POST("updateFood.php")
    fun updateFood(
        @Field("idFood") idFood: Int,
        @Field("nameFood") nameFood: String,
        @Field("price") price: Int,
        @Field("decription") decription: String,
        @Field("imageR") imageR: String,
        @Field("id_type_food") idTypeFood: Int
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("updateTypeFood.php")
    fun updateTypeFood(
        @Field("id_type_food") id_type_food: Int,
        @Field("type_name") type_name: String,
        @Field("image") image: String,

    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("updateUser.php")
    fun updateUser(
        @Field("email_new") email_new: String,
        @Field("phone_number") phone_number: String,
        @Field("email_old") email_old: String
        ): Call<TypeFoodResponse>


    @FormUrlEncoded
    @POST("insertTypeFood.php")
    fun insertTypeFood(
        @Field("type_name")
        type_name: String?,
        @Field("image")
        image: String?,
    ) : Call<TypeFoodResponse>
}
