package com.neeraj.smartcampus.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.modules.foodInfo

class userFoodMenuCafeChildAdapter(options: FirebaseRecyclerOptions<foodInfo>):
    FirebaseRecyclerAdapter<foodInfo, userFoodMenuCafeChildAdapter.myViewHolder>(options){

    lateinit var databaseReference: DatabaseReference

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        // creating viewHolder and getting all the required views by their Ids
        val name = itemView.findViewById<TextView>(R.id.foodNameTextView)
        val price = itemView.findViewById<TextView>(R.id.foodPriceTextView)
        val quantity = itemView.findViewById<TextView>(R.id.foodQuantityTextView)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_food_menu_blocks, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, model: foodInfo) {

        // setting the text fields with the values obtained from the firebase in the recyclerView
        holder.name.text = model.foodName
        holder.price.text = "Rs. ${model.foodPrice}"

    }

}