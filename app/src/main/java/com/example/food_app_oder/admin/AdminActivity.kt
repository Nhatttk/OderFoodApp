package com.example.food_app_oder.admin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.adapter.FetchingFoodFragment
import com.example.food_app_oder.login.LoginActivity

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class AdminActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin)

        supportActionBar?.setTitle("Admin")
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        addFragment(AddFoodFragment())
//        replaceFragment(AddFoodFragment())
//        replaceFragment(MainFragment())

        getFragmentItem()

    }

    lateinit var fragmentManager : FragmentManager
    lateinit var fragmentTransition : FragmentTransaction
    private fun addFragment(fragment : Fragment) {
        fragmentManager = supportFragmentManager
        fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.add(R.id.fragment_container_view_tag, fragment)
        fragmentTransition.commit()
    }

    private fun getFragmentItem() {
        val smoothbtBar = findViewById<BottomNavigationView>(R.id.smoothbtBar)
        val nav_dr = findViewById<NavigationView>(R.id.nav_admin)
        smoothbtBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.addFragment -> replaceFragment(AddFoodFragment())
                R.id.fetchingFragment -> replaceFragment(FetchingFoodFragment())
            }
            true
        }
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
        private fun replaceFragment(fragment : Fragment) {
            fragmentManager = supportFragmentManager
            fragmentTransition = fragmentManager.beginTransaction()
            fragmentTransition.replace(R.id.fragment_container_view_tag, fragment)
            fragmentTransition.commit()
        }



}

