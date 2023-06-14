package com.example.food_app_oder.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.food_app_oder.R
import com.example.food_app_oder.activity.MainActivity
import com.example.food_app_oder.databinding.ActivityLoginBinding
import com.example.food_app_oder.databinding.ActivityMainBinding
import com.example.food_app_oder.fragment.CartFragment
import com.example.food_app_oder.fragment.MainFragment
import com.example.food_app_oder.fragment.SearchFragment

private lateinit var binding: ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Kiểm tra trạng thái đăng nhập từ SharedPreferences
        val sharedPreferences = getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean(MyApplication.KEY_IS_LOGGED_IN, false)
        if (isLoggedIn) {

            // Người dùng đã đăng nhập, chuyển đến màn hình chính
            val intent = Intent(this, MainActivity::class.java)
            intent.putExtra("email", sharedPreferences.getString("emailKey", "NULL"))
            intent.putExtra("phone", sharedPreferences.getString("phoneKey", "NULL"))
            startActivity(intent)
            finish()
        }
        getItemFragment()
    }
    lateinit var fragmentManager : FragmentManager
    lateinit var fragmentTransition : FragmentTransaction
    fun getItemFragment() {
            binding.smoothbtBarLogin.setOnItemSelectedListener {
                when (it.itemId) {
                    R.id.sign_in_tab -> replaceFragment(SignInFragment())
                    R.id.sign_up_tab -> replaceFragment(SignUpFragment())

                }
                true
            }
    }


    private fun replaceFragment(fragment : Fragment) {
        fragmentManager = supportFragmentManager
        fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container_view_login, fragment)
        fragmentTransition.commit()
    }
}