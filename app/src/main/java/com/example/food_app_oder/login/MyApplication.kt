package com.example.food_app_oder.login

import android.app.Application
import android.content.Context
import android.content.Intent
import com.example.food_app_oder.activity.MainActivity

class MyApplication : Application() {

    companion object {
        const val PREFS_NAME = "MyPrefs"
        const val KEY_IS_LOGGED_IN = "isLoggedIn"
    }

    override fun onCreate() {
        super.onCreate()

        // Khởi tạo SharedPreferences
        val sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        // Kiểm tra trạng thái đăng nhập của người dùng
        val isLoggedIn = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)

        // Nếu người dùng đã đăng nhập, chuyển sang màn hình chính
        if (isLoggedIn) {
            val intent = Intent(this, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(intent)
        }
    }

}