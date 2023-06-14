package com.example.food_app_oder.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.apachat.loadingbutton.core.customViews.CircularProgressButton
import com.example.food_app_oder.R
import com.example.food_app_oder.activity.OderHistoryActivity
import com.example.food_app_oder.login.LoginActivity
import com.example.food_app_oder.login.MyApplication
import com.google.android.material.card.MaterialCardView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ProfileFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ProfileFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val sharedPreferences = requireActivity().getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
//        val bundle = arguments
        val email = view.findViewById<TextView>(R.id.email)
        val phone = view.findViewById<TextView>(R.id.phoneNumber)
        val txtLichSuDonHang = view.findViewById<TextView>(R.id.txtLichSuDonHang)
        val valueEmail = sharedPreferences.getString("emailKey", "")
//        val valueEmail = arguments?.getString("intentEmail") // thay "key" bằng tên key mà bạn đã đưa dữ liệu vào trong Bundle
        val valuePhone = sharedPreferences.getString("phoneKey", "")


//        val valuePhone = arguments?.getString("intentPhone") // thay "key" bằng tên key mà bạn đã đưa dữ liệu vào trong Bundle
        email.text = valueEmail
        phone.text = valuePhone
        txtLichSuDonHang.text = "Lịch sử đặt hàng"

        val log_out = view.findViewById<CircularProgressButton>(R.id.log_out)
        log_out.setOnClickListener {

            val sharedPreferences = requireActivity().getSharedPreferences(MyApplication.PREFS_NAME, Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()
            editor.putBoolean(MyApplication.KEY_IS_LOGGED_IN, false)
            editor.apply()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
        }

        val lichsudathang : TextView = view.findViewById(R.id.txtLichSuDonHang)
        lichsudathang.setOnClickListener {
            val intent = Intent(requireActivity(), OderHistoryActivity::class.java)
            startActivity(intent)
        }
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ProfileFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ProfileFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}