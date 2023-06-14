package com.example.food_app_oder.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app_oder.R
import com.example.food_app_oder.adapter.FoodAdapter
import com.example.food_app_oder.adapter.OnItemClickListener
import com.example.food_app_oder.databinding.ActivityFoodDetailsBinding
import com.example.food_app_oder.login.MyApplication
import com.example.food_app_oder.model.CartItem
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.model.FoodCart
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.BreakIterator


class FoodDetails : AppCompatActivity() {
    private lateinit var foodCart : FoodCart
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_food_details)

        var idF : Int = intent.getIntExtra("idFood", 12)
        getFood(idF)


//        val btn = findViewById<Button>(R.id.btn_test)
//        val layoutbtSheet = findViewById<LinearLayout>(R.id.bottom_sheet)

//        txt111.text = intent.getIntExtra("idFood", 12).toString()
    }

//    private var id = intent.getIntExtra("idFood", 1)


    lateinit var adapter : FoodAdapter


    private fun getFood(idF : Int) {

        val recyclerViewDetails : RecyclerView = findViewById(R.id.recyclerviewFoodDetails)
//        id = intent.getIntExtra("idFood", 1)

        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java)
        var service : Call<List<Food>> = client.getFood(idF)
//        service = client.getFood(idF)

        service.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                Toast.makeText(this@FoodDetails, "Calll suscess", Toast.LENGTH_LONG).show()
                val list = response.body()
                if (list != null) {
                    adapter = FoodAdapter(this@FoodDetails, list, object : OnItemClickListener{
                        override fun onItemClick(position: Int) {

                            val bottomSheetDialog = BottomSheetDialog(this@FoodDetails)
//                            val bottomSheetView = layoutInflater.inflate(R.layout.search_fragment, null)

                            val bottomSheetBinding = ActivityFoodDetailsBinding.inflate(layoutInflater)
                            val bottomSheetView = bottomSheetBinding.root
                            bottomSheetDialog.setContentView(bottomSheetView)



                            if (bottomSheetBinding.productImage != null) {
                                Glide.with(this@FoodDetails).load(list.get(position).imageUrl).into(bottomSheetBinding.productImage)
                            }
                            bottomSheetBinding.foodName.text = list[position].name
                            bottomSheetBinding.productPrice.text = list[position].price.toString()


                            bottomSheetView.findViewById<Button>(R.id.cancel).setOnClickListener {
                                bottomSheetDialog.dismiss()
                            }
                            bottomSheetView.findViewById<MaterialCardView>(R.id.decrease).setOnClickListener {
                                var count = bottomSheetBinding.productCount.text.toString().toInt()
                                count--
                                bottomSheetBinding.productCount.text = count.toString()

                            }
                            bottomSheetView.findViewById<MaterialCardView>(R.id.increase).setOnClickListener {
                                var count = bottomSheetBinding.productCount.text.toString().toInt()
                                count++
                                bottomSheetBinding.productCount.text = count.toString()
                            }
                            bottomSheetView.findViewById<Button>(R.id.addToBasket).setOnClickListener {
//                                if (Untils.arrCart.isEmpty()) {
//                                    Untils.arrCart.add(
//                                        CartItem(
//                                            list[position].name,
//                                            list[position].price,
//                                            bottomSheetBinding.productCount.text.toString().toInt()
//                                        )
//                                    )
//                                    Log.d("DEBUG", "arrCart size: ${Untils.arrCart.size}")
//                                    for (cartItem in Untils.arrCart) {
//                                        Log.d("DEBUG", "cartItem: ${cartItem.name} - ${cartItem.price}")
//                                    }
//                                } else {
//                                    Untils.arrCart.add(
//                                        CartItem(
//                                            list[position].name,
//                                            list[position].price,
//                                            bottomSheetBinding.productCount.text.toString().toInt()
//                                        )
//                                    )
//                                    Log.d("DEBUG", "arrCart size: ${Untils.arrCart.size}")
//                                    for (cartItem in Untils.arrCart) {
//                                        Log.d("DEBUG", "cartItem: ${cartItem.name} - ${cartItem.price}")
//                                    }
//                                }
//                                bottomSheetDialog.dismiss()

                                val sharedPreferences = getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
                                val cartJson = sharedPreferences.getString("cartItem", null)
                                foodCart = if (cartJson != null) {
                                    FoodCart.fromJson(cartJson)
                                } else {
                                    FoodCart()
                                }

                                // add food to cart

                                val existingCartItem = foodCart.foods.find { it.id == list[position].id }
                                if (existingCartItem == null) {
                                    foodCart.addFood(CartItem(
                                        list[position].id,
                                        list[position].name,
                                        list[position].price,
                                        bottomSheetBinding.productCount.text.toString().toInt()
                                    ))
                                } else {
                                    existingCartItem.quantity += bottomSheetBinding.productCount.text.toString().toInt()
                                }





                                // Save cart to SharedPreferences
                                val editor = sharedPreferences.edit()
                                editor.putString("cartItem", foodCart.toJson())
                                Log.d("CartFragment", "arrCart: ${foodCart.toJson()}")
                                editor.apply()
                                bottomSheetDialog.dismiss()
                            }
                            bottomSheetDialog.setContentView(bottomSheetView)
                            bottomSheetDialog.show()




                        }

                    })
                    recyclerViewDetails.adapter = adapter
                    recyclerViewDetails.layoutManager = LinearLayoutManager(
                        this@FoodDetails, LinearLayoutManager.VERTICAL, false
                    )
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }






}