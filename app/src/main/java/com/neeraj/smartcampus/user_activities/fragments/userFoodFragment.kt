package com.neeraj.smartcampus.user_activities.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.firebase.database.FirebaseDatabase
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.modules.foodInfo
import com.neeraj.smartcampus.user_activities.food_activities.Cafe96MainActivity
import kotlinx.android.synthetic.main.fragment_user_food.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [userFoodFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
@Suppress("DEPRECATION")
class userFoodFragment : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_user_food, container, false)

        val name = "Veg Burger"
        val price = 50
        val type = "Fast food"

        val name1 = "Noodles"
        val price1 = 40
        val type1 = "Chinese"

        val food = foodInfo(type, name, price.toString())
        val food1 = foodInfo(type1, name1, price1.toString())

//        for(i in 1..5){
//
//            val foodID = FirebaseDatabase.getInstance().getReference("Canteens")
//                .child("Cafe 96")
////                .child(type1)
//                .push().key
//
//            FirebaseDatabase.getInstance().getReference("Cafe 96")
////                .child(type1)
//                .child(foodID!!).setValue(food).addOnCompleteListener {
//
//                }
//        }

        view.cafe96CardView.setOnClickListener {

            startActivity(Intent(context, Cafe96MainActivity::class.java))
            requireActivity().fragmentManager.popBackStack()

        }

        return view

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment userFoodFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            userFoodFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}