package com.example.food_app_oder.fragment

import android.content.Context
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app_oder.R
import com.example.food_app_oder.adapter.FoodAdapter
import com.example.food_app_oder.adapter.OnItemClickListener
import com.example.food_app_oder.databinding.SearchFragmentBinding
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
import java.util.*

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var mList : List<Food>
    private lateinit var foodCart : FoodCart
//    private lateinit var searchView: SearchView

//    val img = view?.findViewById<ImageView>(R.id.productImage)
//    val name = view?.findViewById<TextView>(R.id.foodName)
//    val price = view?.findViewById<AppCompatTextView>(R.id.productPrice)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getFood()

//        searchView = view.findViewById(R.id.search_view)


        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                    filterList(p0)
                return true
            }

        })

    }

    lateinit var adapter : FoodAdapter

    private fun filterList(query : String?) {
        if (query != null) {
            val filteredList = mutableListOf<Food>()

            for (i in mList) {
                if (i.name.lowercase(Locale.ROOT).contains(query)) {
                    filteredList.add(i)
                }
            }
            if (filteredList.isEmpty()) {
                Toast.makeText(requireActivity(), "Không có dữ liệu", Toast.LENGTH_LONG).show()
            }
            else {
                adapter.setFilterList(filteredList)
            }
        }

    }

    private fun getFood() {

//        val recyclerViewDetails : RecyclerView? = view?.findViewById(R.id.searchRecycleView)
//        val sr : MaterialCardView? = view?.findViewById(R.id.searchh)


        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java)
        var service : Call<List<Food>> = client.getSearch()
//        service = client.getFood(idF)

        service.enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                Toast.makeText(requireActivity(), "Calll suscess", Toast.LENGTH_LONG).show()
                val list = response.body()
                if (list != null) {
                    adapter = FoodAdapter(requireActivity(), list, object : OnItemClickListener {
                        override fun onItemClick(position: Int) {


                            val bottomSheetDialog = BottomSheetDialog(requireActivity())
//                            val bottomSheetView = layoutInflater.inflate(R.layout.search_fragment, null)

                            val bottomSheetBinding = SearchFragmentBinding.inflate(layoutInflater)
                            val bottomSheetView = bottomSheetBinding.root
                            bottomSheetDialog.setContentView(bottomSheetView)

                            bottomSheetBinding.searchh.setVisibility(View.GONE)

                            if (binding.productImageS != null) {
                                Glide.with(requireActivity()).load(list.get(position).imageUrl).into(bottomSheetBinding.productImageS)
                            }
                            bottomSheetBinding.foodNameS.text = list[position].name
                            bottomSheetBinding.productPriceS.text = list[position].price.toString()


                            bottomSheetView.findViewById<Button>(R.id.cancel).setOnClickListener {
                                bottomSheetBinding.searchh.setVisibility(View.VISIBLE)
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

//

                                val sharedPreferences = requireActivity().getSharedPreferences(
                                    MyApplication.PREFS_NAME,
                                    Context.MODE_PRIVATE
                                )
                                val cartJson = sharedPreferences.getString("cartItem", null)
                                foodCart = if (cartJson != null) {
                                    FoodCart.fromJson(cartJson)
                                } else {
                                    FoodCart()
                                }

                                // add food to cart

                                val existingCartItem =
                                    foodCart.foods.find { it.id == list[position].id }
                                if (existingCartItem == null) {
                                    foodCart.addFood(
                                        CartItem(
                                            list[position].id,
                                            list[position].name,
                                            list[position].price,
                                            bottomSheetBinding.productCount.text.toString().toInt()
                                        )
                                    )
                                } else {
                                    existingCartItem.quantity += bottomSheetBinding.productCount.text.toString()
                                        .toInt()
                                }


                                // Save cart to SharedPreferences
                                val editor = sharedPreferences.edit()
                                editor.putString("cartItem", foodCart.toJson())
                                Log.d("CartFragment", "arrCart: ${foodCart.toJson()}")
                                editor.apply()
                                bottomSheetDialog.dismiss()


//                            Toast.makeText(requireActivity(), "$position", Toast.LENGTH_LONG).show()


                            }
                            bottomSheetDialog.setContentView(bottomSheetView)
                            bottomSheetDialog.show()
                        }
                    })

                    binding.searchRecycleView.adapter = adapter
                    binding.searchRecycleView.layoutManager = LinearLayoutManager(
                        requireActivity(), LinearLayoutManager.VERTICAL, false
                    )
                    mList = list
                }

            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}