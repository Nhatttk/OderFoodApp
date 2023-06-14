package com.example.food_app_oder.admin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.food_app_oder.R
import com.example.food_app_oder.model.FoodOder
import com.example.food_app_oder.model.Oder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import org.json.JSONArray

class OderAdapter (val context: Context, var data : List<Oder>) : RecyclerView.Adapter<OderAdapter.ViewHolder>() {

    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OderAdapter.ViewHolder {
        val view = inflater.inflate(R.layout.item_oder, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onBindViewHolder(holder: OderAdapter.ViewHolder, position: Int) {
        holder.id_oder.text = "ID: " + data[position].id_oder
        holder.ghichu.text = "Ghi chú: " + data[position].ghichu
        holder.diachi.text = "Địa chỉ: " + data[position].diachi

        val jsonString = """${data[position].danhsachsp}"""

        val jsonArray = JSONArray(jsonString)
        val stringBuilder = StringBuilder()

        for (i in 0 until jsonArray.length()) {
            val jsonObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getInt("id")
            val nameFood = jsonObject.getString("nameFood")
            val price = jsonObject.getInt("price")
            val quantity = jsonObject.getInt("quantity")

            stringBuilder.append("+ ID: $id\n")
            stringBuilder.append("+ Tên món ăn: $nameFood\n")
            stringBuilder.append("+ Giá: $price\n")
            stringBuilder.append("+ Số lượng: $quantity\n\n")
        }

        val resultString = stringBuilder.toString()
        println(resultString)
        println(data[position].danhsachsp)

        holder.danhsachsp.text = "Sản phẩm: \n " + resultString
        holder.email.text = "Email: " + data[position].email
        holder.priceproduct.text = "Tổng tiền: " + data[position].tongtien
    }

    inner class ViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {

        val id_oder : TextView = itemView.findViewById(R.id.id_oder)
        val ghichu : TextView = itemView.findViewById(R.id.ghichu)
        val diachi : TextView = itemView.findViewById(R.id.diachi)
        val danhsachsp : TextView = itemView.findViewById(R.id.danhsachsp)
        val email : TextView = itemView.findViewById(R.id.email)
        val priceproduct : TextView = itemView.findViewById(R.id.priceproduct)
    }
}