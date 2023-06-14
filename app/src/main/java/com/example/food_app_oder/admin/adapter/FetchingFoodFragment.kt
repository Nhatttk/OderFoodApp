package com.example.food_app_oder.admin.adapter

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FetchingFoodFragment : Fragment() {

    lateinit var adapter: FetchingFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fetching_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getViewItem()
    }

    private fun getViewItem() {
        val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            AdminFood::class.java)

        val fetching_recycle_view = view?.findViewById<RecyclerView>(R.id.fetching_recycle_view)

        client.getFood().enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {

                    val list = response.body()
                if (list!= null) {
                    adapter = FetchingFoodAdapter(requireActivity(), list.toMutableList())
                }

                fetching_recycle_view?.adapter = adapter
                fetching_recycle_view?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                Log.d("Food", "arrFood: ${t.message}")
            }

        })



    }


}