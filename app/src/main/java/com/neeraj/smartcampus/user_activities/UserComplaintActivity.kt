package com.neeraj.smartcampus.user_activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.PopupMenu
import androidx.core.content.ContentProviderCompat.requireContext
import com.neeraj.smartcampus.R
import kotlinx.android.synthetic.main.activity_user_complaint.*

class UserComplaintActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_complaint)

//        val items = listOf("Option 1", "Option 2", "Option 3", "Option 4")
//        val adapter = ArrayAdapter(requireContext(), R.layout.item_list, items)
//        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)
        editText.setOnClickListener {
            PopupMenu(this, editText).apply {
                menuInflater.inflate(R.menu.menu_in_transaction, menu)
                setOnMenuItemClickListener { item ->
                    editText.setText(item.title)
                    true
                }
                show()
            }
        }
    }
}