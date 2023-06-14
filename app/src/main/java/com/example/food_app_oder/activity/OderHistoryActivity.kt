package com.example.food_app_oder.activity

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.adapter.OderAdapter
import com.example.food_app_oder.login.MyApplication
import com.example.food_app_oder.model.Oder
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OderHistoryActivity : AppCompatActivity() {
    lateinit var adapter : OderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oder_history)

        getItemView()
    }

    private fun getItemView() {
        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerViewOder)

        val sharedPreferences = getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
        val valueEmail = sharedPreferences.getString("emailKey", "")

        client.lichsudathang(valueEmail).enqueue(object : Callback<List<Oder>> {
            override fun onResponse(call: Call<List<Oder>>, response: Response<List<Oder>>) {
//                Toast.makeText(this@OderActivity, "call ok", Toast.LENGTH_LONG).show()

                val list = response.body()
                if (list.isNullOrEmpty()) {
                    AlertDialog.Builder(this@OderHistoryActivity)
                        .setTitle("Thông báo")
                        .setMessage("Chưa có đơn đặt hàng nào")
                        .setPositiveButton("Đóng", null)
                        .show()
                }
                if (list!= null) {
                    adapter = OderAdapter(this@OderHistoryActivity, list)
                }

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@OderHistoryActivity, LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(call: Call<List<Oder>>, t: Throwable) {
                Toast.makeText(this@OderHistoryActivity, "call not ok", Toast.LENGTH_LONG).show()
            }

        })
    }
}