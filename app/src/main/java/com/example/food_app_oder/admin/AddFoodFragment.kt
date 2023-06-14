package com.example.food_app_oder.admin

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.food_app_oder.R
import com.example.food_app_oder.admin.model.SelectedFoodType
import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.InterfaceFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.textfield.TextInputEditText
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [AddFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AddFoodFragment : Fragment() {
    lateinit var adapterItems : ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_food, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val add_button = view.findViewById<Button>(R.id.add_button)

        getNameTypeFood(view)
        add_button.setOnClickListener {
            insertFood(view)
        }
    }

    private fun insertFood(view: View) {
        val client : InterfaceFood = RetrofitClient.getInstance(Untils.BASE_URL)
            .create(InterfaceFood::class.java)
        val txtNameFood = view.findViewById<TextInputEditText>(R.id.txtNameFood)
        val txtPrice = view.findViewById<TextInputEditText>(R.id.txtPrice)
        val txtDecription = view.findViewById<TextInputEditText>(R.id.txtDecription)
        val txtImageR = view.findViewById<TextInputEditText>(R.id.txtImageR)
        val auto_complete_txt = view.findViewById<AutoCompleteTextView>(R.id.auto_complete_txt)

        if (
            txtNameFood.text.toString().isEmpty() ||
            txtPrice.text.toString().isEmpty() ||
            txtDecription.text.toString().isEmpty()||
            txtImageR.text.toString().isEmpty() ||
            auto_complete_txt.text.toString().isEmpty()
        ) {
            AlertDialog.Builder(requireActivity())
                .setTitle("Thông báo")
                .setMessage("Không được để trống dữ liệu")
                .setPositiveButton("Đóng", null)
                .show()
        }
        else {
            client.insertFood(
                txtNameFood.text.toString(),
                txtPrice.text.toString(),
                txtDecription.text.toString(),
                txtImageR.text.toString(),
                auto_complete_txt.text.toString().substringAfterLast("-").trim().toInt()
            ).enqueue(object : retrofit2.Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    AlertDialog.Builder(requireActivity())
                        .setTitle("Thông báo")
                        .setMessage("Thêm thành công")
                        .setPositiveButton("Đóng", null)
                        .show()
                    txtNameFood.setText("")
                    txtPrice.setText("")
                    txtDecription.setText("")
                    txtImageR.setText("")
                    auto_complete_txt.setText("")
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    Log.d("name", txtNameFood.text.toString())
                    Log.d("gia", txtPrice.text.toString())
                    Log.d("mota", txtDecription.text.toString())
                    Log.d("imgR", txtImageR.text.toString())
                    Log.d(
                        "id_type",
                        auto_complete_txt.text.toString().substringAfterLast("-").trim()
                    )
                }

            })
        }
    }

    private fun getNameTypeFood(view: View) {
        val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)

        client.getNameTypeFood().enqueue(object : retrofit2.Callback<List<SelectedFoodType>> {
            override fun onResponse(call: Call<List<SelectedFoodType>>, response: Response<List<SelectedFoodType>>) {
                val list = response.body()
                if (list != null) {
                    val autoCompleteTxt : AutoCompleteTextView = view.findViewById(R.id.auto_complete_txt)
                    adapterItems = ArrayAdapter<String>(
                        requireActivity(),
                        R.layout.list_item_type_food,
                        list.map { it.type_name + " - " + it.id_type_food })
                    autoCompleteTxt.setAdapter(adapterItems)
                }
            }

            override fun onFailure(call: Call<List<SelectedFoodType>>, t: Throwable) {
                Log.d("DEBUG", t.message.toString())
            }

        })
    }
}