package com.example.food_app_oder.admin.adapter

import android.app.AlertDialog
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.widget.AppCompatAutoCompleteTextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.food_app_oder.R
import com.example.food_app_oder.adapter.CartAdapter
import com.example.food_app_oder.admin.model.SelectedFoodType
import com.example.food_app_oder.databinding.SearchFragmentBinding
import com.example.food_app_oder.model.Food
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

class FetchingFoodAdapter(val context: Context, val data : MutableList<Food>) : RecyclerView.Adapter<FetchingFoodAdapter.ViewHolder>() {
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    lateinit var adapterItems : ArrayAdapter<String>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = inflater.inflate(R.layout.item_update_food, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val foodDetails = data[position]
        holder.foodND.text = foodDetails.name
        holder.foodPD.text = foodDetails.price.toString()
        holder.imageFD?.let {
            Glide.with(context)
                .load(foodDetails.imageUrl)
                .into(it)
        }

        holder.deleteFood.setOnClickListener {
            val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)
            client.deleteFood(data[position].id).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        data.removeAt(holder.getAdapterPosition())
                        notifyItemRemoved(holder.getAdapterPosition())
                        Toast.makeText(context, "Xóa thành công", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    TODO("Not yet implemented")
                }

            })
        }

        holder.editFood.setOnClickListener {
            val view = LayoutInflater.from(context).inflate(R.layout.bottom_sheet_update_food, null)
            val bottomSheetDialog = BottomSheetDialog(context)
            bottomSheetDialog.setContentView(view)
            val txtNameFood = view.findViewById<TextInputEditText>(R.id.txtNameFood)
            val txtPrice = view.findViewById<TextInputEditText>(R.id.txtPrice)
            val txtDecription = view.findViewById<TextInputEditText>(R.id.txtDecription)
            val txtImageR = view.findViewById<TextInputEditText>(R.id.txtImageR)
            val edit_button = view.findViewById<Button>(R.id.edit_button)
            val auto_complete_txt = view.findViewById<AppCompatAutoCompleteTextView>(R.id.auto_complete_txt)

            txtNameFood.setText(data[position].name)
            txtPrice.setText(data[position].price.toString())
            txtDecription.setText(data[position].description)
            txtImageR.setText(data[position].imageUrl)
            auto_complete_txt.setText(data[position].typeId.toString())
            getNameTypeFood(view)
            bottomSheetDialog.show();

            edit_button.setOnClickListener {
                val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)
                val idTypeFood =
                    if (auto_complete_txt.text.toString().contains("-")) {
                        auto_complete_txt.text.toString().substringAfterLast("-").trim().toInt()
                    } else {
                        auto_complete_txt.text.toString().toInt()
                    }
                client.updateFood(
                    data[position].id,
                    txtNameFood.text.toString(),
                    txtPrice.text.toString().toInt(),
                    txtDecription.text.toString(),
                    txtImageR.text.toString(),
                    idTypeFood
                ).enqueue(object : Callback<ResponseBody>{
                    override fun onResponse(
                        call: Call<ResponseBody>,
                        response: Response<ResponseBody>
                    ) {
                        if (response.isSuccessful) {
                            data[holder.getAdapterPosition()].name = txtNameFood.text.toString()
                            data[holder.getAdapterPosition()].price = txtPrice.text.toString().toInt()
                            data[holder.getAdapterPosition()].description = txtDecription.text.toString()
                            data[holder.getAdapterPosition()].imageUrl = txtImageR.text.toString()
                            data[holder.getAdapterPosition()].typeId = idTypeFood
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
                        TODO("Not yet implemented")
                    }

                })
                bottomSheetDialog.dismiss()
                notifyDataSetChanged()
            }
        }


    }



    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val imageFD : ImageView = itemView.findViewById(R.id.imgFD)
        val foodND : TextView = itemView.findViewById(R.id.txtFN)
        val foodPD : TextView = itemView.findViewById(R.id.txtFP)
        val editFood : MaterialCardView = itemView.findViewById(R.id.editFood)
        val deleteFood : MaterialCardView = itemView.findViewById(R.id.deleteFood)
    }

    private fun getNameTypeFood(view: View) {
        val client : AdminFood = RetrofitClient.getInstance(Untils.BASE_URL).create(AdminFood::class.java)

        client.getNameTypeFood().enqueue(object : retrofit2.Callback<List<SelectedFoodType>> {
            override fun onResponse(call: Call<List<SelectedFoodType>>, response: Response<List<SelectedFoodType>>) {
                val list = response.body()
                if (list != null) {
                    val autoCompleteTxt : AutoCompleteTextView = view.findViewById(R.id.auto_complete_txt)
                    adapterItems = ArrayAdapter<String>(context, R.layout.list_item_type_food, list.map { it.type_name + " - " + it.id_type_food })
                    autoCompleteTxt.setAdapter(adapterItems)

                }
            }

            override fun onFailure(call: Call<List<SelectedFoodType>>, t: Throwable) {
                Log.d("DEBUG", t.message.toString())
            }

        })
    }
}