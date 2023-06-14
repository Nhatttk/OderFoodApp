package com.example.food_app_oder.login


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.apachat.loadingbutton.core.customViews.CircularProgressButton
import com.example.food_app_oder.R
import com.example.food_app_oder.model.User
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class SignUpFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val emailEditText = view.findViewById<TextInputEditText>(R.id.emailEditText)
        val passwordEditText = view.findViewById<TextInputEditText>(R.id.passwordEditText)
        val confirmPasswordEditText =
            view.findViewById<TextInputEditText>(R.id.confirmPasswordEditText)
        val phoneNumberEditText = view.findViewById<TextInputEditText>(R.id.phoneNumberEditText)
        val signUpButton = view.findViewById<CircularProgressButton>(R.id.signUpButton)


        val client: InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
            InterfaceFood::class.java
        )

        signUpButton.setOnClickListener {

            if (emailEditText.text.toString().isEmpty()) {
                emailEditText.error = "Vui lòng không để trống email"
            }
            if (phoneNumberEditText.text.toString().isEmpty()) {
                phoneNumberEditText.error = "Vui lòng không để trống số điện thoại"
            }
            if (passwordEditText.text.toString().isEmpty()) {
                AlertDialog.Builder(requireActivity())
                    .setTitle("Thông báo")
                    .setMessage("Vui lòng không để trống mật khẩu")
                    .setPositiveButton("Đóng", null)
                    .show()
            }


            else if (passwordEditText.text.toString().isNotEmpty() && passwordEditText.text.toString().equals(confirmPasswordEditText.text.toString())) {

                client.insertUser(
                    emailEditText.text.toString(),
                    phoneNumberEditText.text.toString(),
                    passwordEditText.text.toString()
                ).enqueue(object : Callback<List<User>> {
                    override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                        AlertDialog.Builder(requireActivity())
                            .setTitle("Thông báo")
                            .setMessage("Đăng ký thành công")
                            .setPositiveButton("Đóng", null)
                            .show()

                    }

                    override fun onFailure(call: Call<List<User>>, t: Throwable) {
                        AlertDialog.Builder(requireActivity())
                            .setTitle("Thông báo")
                            .setMessage("Đăng ký thành công")
                            .setPositiveButton("Đóng", null)
                            .show()
                    }
                })
            }
            else if (passwordEditText.text.toString().isNotEmpty() && !passwordEditText.text.toString().equals(confirmPasswordEditText.text.toString())){
                AlertDialog.Builder(requireActivity())
                    .setTitle("Thông báo")
                    .setMessage("Mật khẩu không giống")
                    .setPositiveButton("Đóng", null)
                    .show()
            }

            emailEditText.text  = null
            phoneNumberEditText.text = null
            passwordEditText.text = null
            confirmPasswordEditText.text = null
        }


    }
}