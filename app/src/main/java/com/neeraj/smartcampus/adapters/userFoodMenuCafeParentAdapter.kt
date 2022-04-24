package com.neeraj.smartcampus.adapters

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.modules.foodInfo
import com.neeraj.smartcampus.modules.foodInfoCategory

class userFoodMenuCafeParentAdapter(options:FirebaseRecyclerOptions<foodInfoCategory>):
    FirebaseRecyclerAdapter<foodInfoCategory, userFoodMenuCafeParentAdapter.myViewHolder>(options) {

    lateinit var databaseReference: DatabaseReference

    // To override LinearLayoutManager by Wrapper, as it crashes the application sometimes
    inner class LinearLayoutManagerWrapper : LinearLayoutManager {
        constructor(context: Context?) : super(context) {}
        constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
            context,
            orientation,
            reverseLayout
        ) {
        }

        constructor(
            context: Context?,
            attrs: AttributeSet?,
            defStyleAttr: Int,
            defStyleRes: Int
        ) : super(context, attrs, defStyleAttr, defStyleRes) {
        }

        override fun supportsPredictiveItemAnimations(): Boolean {
            return false
        }
    }

    inner class myViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        var title = itemView.findViewById<TextView>(R.id.foodTypeTextView)
        var child_recView = itemView.findViewById<RecyclerView>(R.id.cafe96RecView_child)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.item_food_type_details, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int, model: foodInfoCategory) {

        holder.child_recView.layoutManager = LinearLayoutManagerWrapper(holder.title.context, LinearLayoutManager.HORIZONTAL, false)

        val options2: FirebaseRecyclerOptions<foodInfo> = FirebaseRecyclerOptions.Builder<foodInfo>()
            .setQuery(FirebaseDatabase.getInstance().reference.child("Cafe 96").child("Chinese"), foodInfo::class.java)
            .build()

        val childAdapter: userFoodMenuCafeChildAdapter = userFoodMenuCafeChildAdapter(options2)

        holder.child_recView.adapter = childAdapter

    }
}