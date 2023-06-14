package com.example.food_app_oder.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.adapter.OderAdapter
import com.example.food_app_oder.login.LoginActivity
import com.example.food_app_oder.model.Oder
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.navigation.NavigationView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OderActivity : AppCompatActivity() {
    lateinit var adapter: OderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_oder)

        getItemView()
        getFragmentItem()
    }

    private fun getItemView() {
        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(InterfaceFood::class.java)
        val recyclerView : RecyclerView = findViewById(R.id.recyclerViewOder)
        client.thongtindathang().enqueue(object : Callback<List<Oder>> {
            override fun onResponse(call: Call<List<Oder>>, response: Response<List<Oder>>) {
//                Toast.makeText(this@OderActivity, "call ok", Toast.LENGTH_LONG).show()

                val list = response.body()
                if (list!= null) {
                    adapter = OderAdapter(this@OderActivity, list)
                }

                recyclerView.adapter = adapter
                recyclerView.layoutManager = LinearLayoutManager(this@OderActivity, LinearLayoutManager.VERTICAL, false)
            }

            override fun onFailure(call: Call<List<Oder>>, t: Throwable) {
                Toast.makeText(this@OderActivity, "call not ok", Toast.LENGTH_LONG).show()
            }

        })
    }

    private fun getFragmentItem() {

        val nav_dr = findViewById<NavigationView>(R.id.nav_admin)

        nav_dr.setNavigationItemSelectedListener { menuItem  ->
            when (menuItem.itemId) {
                R.id.foodTable -> {
                    val intent = Intent(this, AdminActivity::class.java)
                    startActivity(intent)
                }
                R.id.typeFoodTable -> {
                    val intent = Intent(this, AdminType::class.java)
                    startActivity(intent)
                }
                R.id.userTable -> {
                    val intent = Intent(this, UserAdmin::class.java)
                    startActivity(intent)
                }
                R.id.home -> {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
                R.id.oder -> {
                    val intent = Intent(this, OderActivity::class.java)
                    startActivity(intent)
                }



            }
            true
        }
    }
}