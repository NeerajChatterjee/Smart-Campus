package com.neeraj.smartcampus.user_activities.food_activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.FirebaseDatabase
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.adapters.userFoodMenuCafeChildAdapter
import com.neeraj.smartcampus.adapters.userFoodMenuCafeParentAdapter
import com.neeraj.smartcampus.modules.foodInfo
import com.neeraj.smartcampus.modules.foodInfoCategory
import kotlinx.android.synthetic.main.activity_cafe96_main.*

class Cafe96MainActivity : AppCompatActivity() {

//    lateinit var parentAdapter: userFoodMenuCafeParentAdapter
        lateinit var childAdapter: userFoodMenuCafeChildAdapter
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cafe96_main)

//        cafe96RecView_parent.layoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.VERTICAL, false)
        cafe96RecView_parent.layoutManager = LinearLayoutManagerWrapper(this, LinearLayoutManager.HORIZONTAL, false)

//        val options: FirebaseRecyclerOptions<foodInfoCategory> = FirebaseRecyclerOptions.Builder<foodInfoCategory>()
//            .setQuery(FirebaseDatabase.getInstance().reference.child("Cafe 96").child("Chinese"), foodInfoCategory::class.java)
//            .build()

        val options: FirebaseRecyclerOptions<foodInfo> = FirebaseRecyclerOptions.Builder<foodInfo>()
            .setQuery(FirebaseDatabase.getInstance().reference.child("Cafe 96"), foodInfo::class.java)
            .build()

//        parentAdapter = userFoodMenuCafeParentAdapter(options)
        childAdapter = userFoodMenuCafeChildAdapter(options)
        cafe96RecView_parent.adapter = childAdapter



    }

    override fun onStart() {
        super.onStart()
        childAdapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        childAdapter.stopListening()
    }
}