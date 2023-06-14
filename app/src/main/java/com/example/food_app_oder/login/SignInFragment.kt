package com.example.food_app_oder.login

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.fragment.app.Fragment
import com.apachat.loadingbutton.core.customViews.CircularProgressButton
import com.example.food_app_oder.R
import com.example.food_app_oder.activity.FoodDetails
import com.example.food_app_oder.activity.MainActivity
import com.example.food_app_oder.admin.AdminActivity
import com.example.food_app_oder.admin.model.ResponLogin
import com.example.food_app_oder.model.Food
import com.example.food_app_oder.model.User
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignInFragment : Fragment()  {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btn_sign_in = view.findViewById<CircularProgressButton>(R.id.btn_sign_in)
        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)


        val client: InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java
        )
//        var service : Call<List<User>> = client.login()
        btn_sign_in.setOnClickListener {
            if (emailEditText.text.toString().equals("admin@gmail.com") && passwordEditText.text.toString().equals("123123")) {
                    val intent = Intent(requireActivity(), AdminActivity::class.java)
                    startActivity(intent)
            }
            else if (emailEditText.text.toString().isEmpty()) {
                emailEditText.error = "Email không được để trống"
            }
            else if (passwordEditText.text.toString().isEmpty()) {
                androidx.appcompat.app.AlertDialog.Builder(requireActivity())
                    .setTitle("Thông báo")
                    .setMessage("Mật khẩu không được để trống")
                    .setPositiveButton("Đóng", null)
                    .show()
            }
            else {
                client.login(emailEditText.text.toString(), passwordEditText.text.toString()).enqueue(object : Callback<ResponLogin> {
                    override fun onResponse(call: Call<ResponLogin>, response: Response<ResponLogin>) {
                        if (response.isSuccessful) {
                            val jsonObject = response.body()
                            if (jsonObject?.success == true) {
                                Toast.makeText(requireActivity(), "Login ok", Toast.LENGTH_LONG).show()
                                val user : List<User>? = jsonObject?.result
                                val sharedPreferences = requireContext().getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
                                val editor = sharedPreferences.edit()
                                editor.putBoolean(MyApplication.KEY_IS_LOGGED_IN, true)
                                editor.putString("emailKey", user?.get(0)?.email)
                                editor.putString("phoneKey", user?.get(0)?.phone)
                                editor.apply()
                                Log.d("DEBUG", jsonObject?.result.toString())
                                val intent = Intent(requireActivity(), MainActivity::class.java)

                                if (user != null) {
                                    intent.putExtra("email", user.get(0).email)
                                    intent.putExtra("phone", user.get(0).phone)
                                }
                                startActivity(intent)

                            } else if (jsonObject?.success == false){
                                AlertDialog.Builder(requireContext())
                                    .setTitle("Thông báo")
                                    .setMessage("Tài khoản hoặc mật khẩu không đúng.")
                                    .setPositiveButton("Đóng", null)
                                    .show()
                            }


                        }
                        else {
                            Toast.makeText(requireActivity(), "Login không ok", Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun onFailure(call: Call<ResponLogin>, t: Throwable) {
                        Toast.makeText(requireActivity(), "login failed", Toast.LENGTH_LONG).show()
                    }

                })
            }
        }
    }
}

