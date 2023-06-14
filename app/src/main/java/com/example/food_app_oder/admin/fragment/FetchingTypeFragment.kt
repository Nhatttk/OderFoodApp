package com.example.food_app_oder.admin.fragment

import TypeFood
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.adapter.FetchingFoodAdapter
import com.example.food_app_oder.admin.adapter.FetchingTypeFoodAdapter
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FetchingTypeFragment : Fragment() {

    lateinit var adapter : FetchingTypeFoodAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fetching_type, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getViewItem()
    }

    private fun getViewItem() {
        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java)

        val fetching_recycle_view = view?.findViewById<RecyclerView>(R.id.fetching_recycle_view)

        client.getTypeFood().enqueue(object : Callback<List<TypeFood>> {
            override fun onResponse(call: Call<List<TypeFood>>, response: Response<List<TypeFood>>) {

                val list = response.body()
                if (list!= null) {
                    adapter = FetchingTypeFoodAdapter(requireActivity(), list.toMutableList())
                }

                fetching_recycle_view?.adapter = adapter
                fetching_recycle_view?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(call: Call<List<TypeFood>>, t: Throwable) {
                Log.d("Food", "arrFood: ${t.message}")
            }

        })



    }
}