package com.example.food_app_oder.admin.adapter


import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView

import com.example.food_app_oder.R

import com.example.food_app_oder.admin.model.TypeFoodResponse
import com.example.food_app_oder.model.User

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

class UserAdapter(val context: Context, var data : MutableList<User>) : RecyclerView.Adapter<UserAdapter.ViewHolder>() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_user, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val user = data[position]
        holder.txtemail.text = user.email
        holder.txtsdt.text = user.phone
        holder.img.setImageResource(R.drawable.ic_baseline_person_24)

        holder.deleteFood.setOnClickListener {
            val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)
            client.deleteUser(data[position].email).enqueue(object : Callback<TypeFoodResponse> {
                override fun onResponse(
                    call: Call<TypeFoodResponse>,
                    response: Response<TypeFoodResponse>
                ) {
                    val jsonObject = response.body()


                    if (jsonObject?.success == true) {
                        data.removeAt(holder.getAdapterPosition())
                        notifyItemRemoved(holder.getAdapterPosition())

                        Toast.makeText(context, "Xóa dữ liệu thành công", Toast.LENGTH_LONG).show()

                    } else if (jsonObject?.success == false){
                        AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Tài khoản này đã liên kết với một đơn hàng nên không thể xóa")
                            .setPositiveButton("Đóng", null)
                            .show()
                    }
                }

                override fun onFailure(call: Call<TypeFoodResponse>, t: Throwable) {
                    AlertDialog.Builder(context)
                        .setTitle("Thông báo")
                        .setMessage("Tài khoản này đã liên kết với một đơn hàng nên không thể xóa")
                        .setPositiveButton("Đóng", null)
                        .show()
                }

            })
        }

        holder.editFood.setOnClickListener {

            val view = LayoutInflater.from(context).inflate(R.layout.bottomsheet_update_user, null)
            val bottomSheetDialog = BottomSheetDialog(context)
            bottomSheetDialog.setContentView(view)

            val txtEmail = view.findViewById<TextInputEditText>(R.id.txtEmail)
            val txtPhone = view.findViewById<TextInputEditText>(R.id.txtPhone)
            val edit_button = view.findViewById<Button>(R.id.edit_button)

            txtEmail.setText(data[position].email)
            txtPhone.setText(data[position].phone)
            bottomSheetDialog.show();

            val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)

            edit_button.setOnClickListener {
                client.updateUser(txtEmail.text.toString(), txtPhone.text.toString(), data[position].email).enqueue(object : Callback<TypeFoodResponse> {
                    override fun onResponse(
                        call: Call<TypeFoodResponse>,
                        response: Response<TypeFoodResponse>
                    ) {
                        val jsonObject = response.body()
                        if (jsonObject?.success == true) {
                            AlertDialog.Builder(context)
                                .setTitle("Thông báo")
                                .setMessage("Sửa dữ liệu thành công")
                                .setPositiveButton("Đóng", null)
                                .show()

                            data[holder.getAdapterPosition()].email = txtEmail.text.toString()
                            data[holder.getAdapterPosition()].phone = txtPhone.text.toString()

                            // Notify the adapter to update the item
                            notifyItemChanged(holder.getAdapterPosition())
                        }
                        else {
                            AlertDialog.Builder(context)
                                .setTitle("Thông báo")
                                .setMessage("Sửa dữ liệu không thành công")
                                .setPositiveButton("Đóng", null)
                                .show()
                        }
                    }

                    override fun onFailure(call: Call<TypeFoodResponse>, t: Throwable) {
                        AlertDialog.Builder(context)
                            .setTitle("Thông báo")
                            .setMessage("Có lỗi xảy ra : ${t.message}")
                            .setPositiveButton("Đóng", null)
                            .show()
                    }

                })
                bottomSheetDialog.dismiss()

            }






        }


    }



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val txtemail : TextView = itemView.findViewById(R.id.txtemail)
        val img : ImageView = itemView.findViewById(R.id.imgFD)
        val txtsdt : TextView = itemView.findViewById(R.id.txtsdt)
        val deleteFood : MaterialCardView = itemView.findViewById(R.id.deleteFood)
        val editFood : MaterialCardView = itemView.findViewById(R.id.editFood)
    }

}