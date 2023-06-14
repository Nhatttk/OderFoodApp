package com.example.food_app_oder.admin.adapter

import TypeFood
import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app_oder.R

import com.example.food_app_oder.admin.model.TypeFoodResponse

import com.example.food_app_oder.retrofit.AdminFood
import com.example.food_app_oder.retrofit.RetrofitClient
import com.example.food_app_oder.untils.Untils
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textfield.TextInputEditText


import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FetchingTypeFoodAdapter(val context: Context, val data : MutableList<TypeFood>) : RecyclerView.Adapter<FetchingTypeFoodAdapter.ViewHolder>() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var adapterItems : ArrayAdapter<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_update_type_food, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val typeFood = data[position]
        holder.foodND.text = typeFood.name

        holder.imageFD?.let {
            Glide.with(context)
                .load(typeFood.imagePath)
                .into(it)
        }

        holder.deleteFood.setOnClickListener {
            val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)
            client.deleteTypeFood(data[position].id).enqueue(object : Callback<TypeFoodResponse> {
                override fun onResponse(
                    call: Call<TypeFoodResponse>,
                    response: Response<TypeFoodResponse>
                ) {
                    val jsonObject = response.body()


                    if (jsonObject?.success == true) {
                        data.removeAt(holder.getAdapterPosition())
                        notifyItemRemoved(holder.getAdapterPosition())
                        AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Xóa thành công")
                            .setPositiveButton("Đóng", null)
                            .show()
                    } else {
                        Toast.makeText(context, "${jsonObject?.message}", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<TypeFoodResponse>, t: Throwable) {
                    Toast.makeText(context, "${t.message}", Toast.LENGTH_LONG).show()
                    Log.d("FoodMessaged", "message: ${t.message}")
                }

            })
        }

        holder.editFood.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_update_type_food, null)
            val bottomSheetDialog = BottomSheetDialog(context)
            bottomSheetDialog.setContentView(view)

            val txtNameFood = view.findViewById<TextInputEditText>(R.id.txtNameFood)
            val txtImageR = view.findViewById<TextInputEditText>(R.id.txtImageR)
            val edit_button = view.findViewById<Button>(R.id.edit_button)

            txtNameFood.setText(data[position].name)
            txtImageR.setText(data[position].imagePath)
            bottomSheetDialog.show();

            edit_button.setOnClickListener {
                val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)

                data[position].id?.let { it1 ->
                    client.updateTypeFood(
                        it1,
                        txtNameFood.text.toString(),
                        txtImageR.text.toString()
                    ).enqueue(object : Callback<ResponseBody> {
                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                data[holder.getAdapterPosition()].name = txtNameFood.text.toString()
                                data[holder.getAdapterPosition()].imagePath = txtImageR.text.toString()

                                // Notify the adapter to update the item
                                notifyItemChanged(holder.getAdapterPosition())
                                AlertDialog.Builder(context)
                                    .setTitle("Thông báo")
                                    .setMessage("Cập nhật thành công")
                                    .setPositiveButton("Đóng", null)
                                    .show()
                            }
                        }

                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Toast.makeText(context, "Gọi dữ liệu thất bại", Toast.LENGTH_LONG).show()
                        }
                    })

                }

                bottomSheetDialog.dismiss()

            }
        }


    }



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageFD : ImageView = itemView.findViewById(R.id.imgFD)
        val foodND : TextView = itemView.findViewById(R.id.txtFN)
        val editFood : MaterialCardView = itemView.findViewById(R.id.editFood)
        val deleteFood : MaterialCardView = itemView.findViewById(R.id.deleteFood)
    }

}