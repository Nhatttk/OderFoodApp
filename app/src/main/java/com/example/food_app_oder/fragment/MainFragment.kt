package com.example.food_app_oder.fragment

import TypeFood
import TypeOfFoodAdapter
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.activity.FoodDetails
//import com.example.food_app_oder.activity.binding
import com.example.food_app_oder.adapter.OnItemClickListener
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.bottomsheet.BottomSheetDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainFragment : Fragment() {


    lateinit var adapter : TypeOfFoodAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }
    val txt2 = view?.findViewById<TextView>(R.id.tx2)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)



        val recyclerviewTrangChu : RecyclerView = view.findViewById(R.id.recyclerviewTrangChu)

        val drawerLayout = view.findViewById<DrawerLayout>(R.id.drawerLayout)
        drawerLayout.layoutParams = DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

        getTypeFood(recyclerviewTrangChu)
    }

    private val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL).create(
        InterfaceFood::class.java)
    private var service : Call<List<TypeFood>> = client.getTypeFood()

    private fun getTypeFood(recyclerviewTrangChu : RecyclerView) {

        service = client.getTypeFood()
        service.enqueue(object : Callback<List<TypeFood>> {
            override fun onResponse(call: Call<List<TypeFood>>, response: Response<List<TypeFood>>) {
                Toast.makeText(requireActivity(), "Calll suscess", Toast.LENGTH_LONG).show()
                val list = response.body()
                if (list != null) {
                    adapter = TypeOfFoodAdapter(requireActivity(), list, object :
                        OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val intent = Intent(requireActivity(), FoodDetails::class.java)
                            intent.putExtra("idFood", list.get(position).id)
                            startActivity(intent)
                        }

                    })
                    recyclerviewTrangChu.adapter = adapter
//                    recyclerView.adapter = TypeOfFoodAdapter(context, dataSource)
                    recyclerviewTrangChu.layoutManager = GridLayoutManager(requireActivity(), 2)
                }



            }

            override fun onFailure(call: Call<List<TypeFood>>, t: Throwable) {

                Toast.makeText(requireActivity(), "call failed", Toast.LENGTH_LONG).show()
                txt2?.text = t.message
                print(t.message)
            }

        })
    }

}