package com.example.food_app_oder.activity

//import kotlinx.coroutines.flow.internal.NoOpContinuation.context

import android.app.AlertDialog
import android.os.Bundle
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.food_app_oder.R
import com.example.food_app_oder.databinding.ActivityMainBinding
import com.example.food_app_oder.fragment.CartFragment
import com.example.food_app_oder.fragment.MainFragment
import com.example.food_app_oder.fragment.ProfileFragment
import com.example.food_app_oder.fragment.SearchFragment
import com.google.android.material.bottomsheet.BottomSheetDialog


private lateinit var binding: ActivityMainBinding


class MainActivity : AppCompatActivity() {

//    val compositeDisposable = CompositeDisposable()
//    var apiFood : InterfaceFood?= null
//
//    lateinit var adapter : TypeOfFoodAdapter
    lateinit var intentEmail :String
    lateinit var itentPhone :String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)



         intentEmail = intent.getStringExtra("email").toString()
         itentPhone = intent.getStringExtra("phone").toString()



//        val bottomSheetDialog = BottomSheetDialog(this)
//        val bottomSheetView = layoutInflater.inflate(R.layout.activity_main, null)
//
//        bottomSheetDialog.setContentView(bottomSheetView)
//        bottomSheetDialog.dismiss()




        addFragment(MainFragment())
//        replaceFragment(MainFragment())

        getFragmentItem()
//        replaceFragment(SearchFragment())
        // Hiển thị dữ liệu Loại Thức ăn
//            getTypeFood()

//        getEventClick()


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
        binding.smoothbtBar.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.categoriesFragment -> replaceFragment(MainFragment())
                R.id.searchFragment -> replaceFragment(SearchFragment())
                R.id.basketFragment -> replaceFragment(CartFragment())
                R.id.profileFragment -> {

                    val bundle = Bundle()
                    bundle.putString("intentEmail", intentEmail)
                    bundle.putString("intentPhone", itentPhone)
                    val fragment = ProfileFragment()
                    fragment.arguments = bundle
                    replaceFragment(fragment)
                }

            }
            true
        }

//            when(it) {
//                R.id.categoriesFragment -> replaceFragment(MainFragment())
//                R.id.searchFragment -> replaceFragment(SearchFragment())
//        }
//            true


    }

    private fun replaceFragment(fragment : Fragment) {
        fragmentManager = supportFragmentManager
        fragmentTransition = fragmentManager.beginTransaction()
        fragmentTransition.replace(R.id.fragment_container_view_tag, fragment)
        fragmentTransition.commit()
    }


//    private val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(InterfaceFood::class.java)
//    private var service : Call<List<TypeFood>> = client.getTypeFood()
//
//    private fun getTypeFood() {
//        service = client.getTypeFood()
//        service.enqueue(object : Callback<List<TypeFood>>{
//            override fun onResponse(call: Call<List<TypeFood>>, response: Response<List<TypeFood>>) {
//                Toast.makeText(this@MainActivity, "Calll suscess", Toast.LENGTH_LONG).show()
//                val list = response.body()
//                if (list != null) {
//                    adapter = TypeOfFoodAdapter(this@MainActivity, list, object : OnItemTypeFoodClickListener{
//                        override fun onItemClick(position: Int) {
//                            val intent = Intent(this@MainActivity, FoodDetails::class.java)
//                            intent.putExtra("idFood", list.get(position).id)
//                            startActivity(intent)
//                        }
//
//                    })
//                    binding.recyclerviewTrangChu.adapter = adapter
////                    recyclerView.adapter = TypeOfFoodAdapter(context, dataSource)
//                    binding.recyclerviewTrangChu.layoutManager = GridLayoutManager(this@MainActivity, 2)
//                }
//
//
//
//            }
//
//            override fun onFailure(call: Call<List<TypeFood>>, t: Throwable) {
//                Toast.makeText(this@MainActivity, "call failed", Toast.LENGTH_LONG).show()
//                binding.tx2.text = t.message
//                print(t.message)
//            }
//
//        })
//    }


//    private fun ActionBar() {
//        setSupportActionBar(binding.toobarTrangChu)
//        getSupportActionBar()?.setDisplayHomeAsUpEnabled(true)
//        binding.toobarTrangChu.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size)
//        binding.toobarTrangChu.setNavigationOnClickListener(View.OnClickListener {
//            binding.drawerLayout.openDrawer(GravityCompat.START)
//        })
//    }



}




