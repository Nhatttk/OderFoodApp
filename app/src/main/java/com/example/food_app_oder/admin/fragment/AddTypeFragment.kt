package com.example.food_app_oder.admin.fragment

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.model.TypeFoodResponse
import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddTypeFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_type, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val add_button = view.findViewById<Button>(R.id.add_button)
        val txtNameFood = view.findViewById<TextInputEditText>(R.id.txtNameFood)
        val txtImageR = view.findViewById<TextInputEditText>(R.id.txtImageR)

        add_button.setOnClickListener {
            val client: AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
                AdminFood::class.java
            )
            if (txtNameFood.text.toString().isEmpty() || txtImageR.text.toString().isEmpty()) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Thông báo")
                    .setMessage("Không được để trống dữ liệu")
                    .setPositiveButton("Đóng", null)
                    .show()
            } else {
                client.insertTypeFood(txtNameFood.text.toString(), txtImageR.text.toString())
                    .enqueue(object : Callback<TypeFoodResponse> {
                        override fun onResponse(
                            call: Call<TypeFoodResponse>,
                            response: Response<TypeFoodResponse>
                        ) {
                            val jsonObject = response.body()


                            if (jsonObject?.success == true) {
                                AlertDialog.Builder(requireActivity())
                                    .setTitle("Thông báo")
                                    .setMessage("Thêm thành công")
                                    .setPositiveButton("Đóng", null)
                                    .show()
                            } else {
                                AlertDialog.Builder(requireActivity())
                                    .setTitle("Thông báo")
                                    .setMessage("Thêm không thành công")
                                    .setPositiveButton("Đóng", null)
                                    .show()
                            }

                        }

                        override fun onFailure(call: Call<TypeFoodResponse>, t: Throwable) {
                            AlertDialog.Builder(requireActivity())
                                .setTitle("Thông báo")
                                .setMessage("Thêm không thành công")
                                .setPositiveButton("Đóng", null)
                                .show()
                        }

                    })
            }
        }
    }



}