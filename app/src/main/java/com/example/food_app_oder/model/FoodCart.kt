package com.example.food_app_oder.model

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

    class FoodCart {
    var foods: MutableList<CartItem> = mutableListOf()

    fun addFood(food: CartItem) {

        foods.add(food)

    }

    fun removeFood(product: CartItem) {
        foods.remove(product)
    }

    fun toJson(): String {
        return Gson().toJson(foods)
    }

    companion object {
        fun fromJson(json: String): FoodCart {
            val cart = FoodCart()
            cart.foods = Gson().fromJson(json, object : TypeToken<List<CartItem>>() {}.type)
            return cart
        }
    }


}
