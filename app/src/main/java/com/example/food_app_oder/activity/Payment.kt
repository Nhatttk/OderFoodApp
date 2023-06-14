package com.example.food_app_oder.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.food_app_oder.admin.model.ResponMagiamgia
import com.example.food_app_oder.databinding.ActivityPaymentBinding
import com.example.food_app_oder.login.LoginActivity
import com.example.food_app_oder.login.MyApplication
import com.example.food_app_oder.model.Oder
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import retrofit2.Call
import retrofit2.Response

private lateinit var binding: ActivityPaymentBinding
class Payment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(InterfaceFood::class.java)
        val sharedPreferences = getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
//        Log.d("Email", "emailKey: ${sharedPreferences.getString("emailKey", "")}")
        binding.orderNow.setOnClickListener {
//            Log.d("Email", "emailKey: ${binding.orderNote.text}")
//            Log.d("Email", "emailKey: ${binding.orderAddress.text}")
//            Log.d("Email", "emailKey: ${intent.getStringExtra("listCart")}")

            if (binding.orderMaGiamGia.text.toString().isNotEmpty()) {

                // Nếu dữ liệu mã giảm giá không rỗng thì sẽ kiểm tra mã giảm giá có hợp lệ hay không
                client.magiamgia(binding.orderMaGiamGia.text.toString()).enqueue(object : retrofit2.Callback<ResponMagiamgia>{
                    override fun onResponse(call: Call<ResponMagiamgia>, response: Response<ResponMagiamgia>) {
                        val jsonObject = response.body()
                        if (jsonObject?.success == true) {
                            client.insertOder(
                                binding.orderNote.text.toString(),
                                binding.orderAddress.text.toString(),
                                intent.getStringExtra("listCart"),
                                intent.getIntExtra("totalAmount", 5000000) - jsonObject.result[0].sotiengiam,
                                sharedPreferences.getString("emailKey", "")
                            ).enqueue(object : retrofit2.Callback<List<Oder>> {
                                override fun onResponse(call: Call<List<Oder>>, response: Response<List<Oder>>) {
                                    AlertDialog.Builder(this@Payment)
                                        .setTitle("Thông báo")
                                        .setMessage("Đặt hàng thành công")
                                        .setPositiveButton("Đóng", null)
                                        .show()
                                }

                                override fun onFailure(call: Call<List<Oder>>, t: Throwable) {
                                    AlertDialog.Builder(this@Payment)
                                        .setTitle("Thông báo")
                                        .setMessage("Đặt hàng thành công")
                                        .setPositiveButton("Đóng", null)
                                        .show()
                                }

                            })

                        }
                        else {
                            AlertDialog.Builder(this@Payment)
                                .setTitle("Thông báo")
                                .setMessage("Mã giảm giá không đúng")
                                .setPositiveButton("Đóng", null)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<ResponMagiamgia>, t: Throwable) {
                        TODO("Not yet implemented")
                    }

                })
            }
            else {
                // Nếu mã giảm giá rỗng thì không cần trừ số tổng số tiền
                            client.insertOder(
                                binding.orderNote.text.toString(),
                                binding.orderAddress.text.toString(),
                                intent.getStringExtra("listCart"),
                                intent.getIntExtra("totalAmount", 5000000),
                                sharedPreferences.getString("emailKey", "")
                            ).enqueue(object : retrofit2.Callback<List<Oder>> {
                                override fun onResponse(call: Call<List<Oder>>, response: Response<List<Oder>>) {
                                    AlertDialog.Builder(this@Payment)
                                        .setTitle("Thông báo")
                                        .setMessage("Đặt hàng thành công")
                                        .setPositiveButton("Đóng", null)
                                        .show()
                                }

                                override fun onFailure(call: Call<List<Oder>>, t: Throwable) {
                                    AlertDialog.Builder(this@Payment)
                                        .setTitle("Thông báo")
                                        .setMessage("Đặt hàng thành công")
                                        .setPositiveButton("Đóng", null)
                                        .show()
                                }

                            })
//                            val intent = Intent(this@Payment, LoginActivity::class.java)
//                            startActivity(intent)


                    }


            }



//            client.insertOder(
//                binding.orderNote.text.toString(),
//                binding.orderAddress.text.toString(),
//                intent.getStringExtra("listCart"),
//                sharedPreferences.getString("emailKey", "")
//            ).enqueue(object : retrofit2.Callback<List<Oder>> {
//                override fun onResponse(call: Call<List<Oder>>, response: Response<List<Oder>>) {
//                    if (response.isSuccessful) {
//                        Toast.makeText(this@Payment, "Insert success", Toast.LENGTH_LONG).show()
//                    }
//                    else {
//                        Toast.makeText(this@Payment, "Insert failed 2222", Toast.LENGTH_LONG).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<List<Oder>>, t: Throwable) {
//                    Toast.makeText(this@Payment, "Insert failed", Toast.LENGTH_LONG).show()
//                }
//
//            })


        }
    }
