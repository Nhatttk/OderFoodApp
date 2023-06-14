package com.example.food_app_oder.admin

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.adapter.FetchingTypeFoodAdapter
import com.example.food_app_oder.admin.adapter.UserAdapter
import com.example.food_app_oder.model.User
import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserFragment : Fragment() {

    lateinit var adapter : UserAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        getViewItem()
    }

    private fun getViewItem() {
        val fetching_recycle_view = view?.findViewById<RecyclerView>(R.id.fetching_recycle_view)
        val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)
        client.getDataUser().enqueue(object : Callback<List<User>>{
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                val list = response.body()
                if (list!= null) {
                    adapter = UserAdapter(requireActivity(), list.toMutableList())
                }

                fetching_recycle_view?.adapter = adapter
                fetching_recycle_view?.layoutManager = LinearLayoutManager(requireActivity(), LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

}