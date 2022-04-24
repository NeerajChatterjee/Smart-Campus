package com.neeraj.smartcampus.user_activities.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.user_activities.UserComplaintActivity
import com.neeraj.smartcampus.user_activities.UserHostelContactsActivity
import com.neeraj.smartcampus.user_activities.UserLeaveActivity
import kotlinx.android.synthetic.main.fragment_user_hostel.view.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [userHostelFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class userHostelFragment : Fragment() {
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
        var view: View = inflater.inflate(R.layout.fragment_user_hostel, container, false)

        view.complaint.setOnClickListener{
            startActivity(Intent(context, UserComplaintActivity::class.java))
        }
        view.leaveApplication.setOnClickListener{
            startActivity(Intent(context, UserLeaveActivity::class.java))
        }
        view.ambulance.setOnClickListener{
            val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:" + "7417348469"))
            startActivity(intent)
//            val number = "7417348469"
//            val callIntent = Intent(Intent.ACTION_CALL)
//            callIntent.data = Uri.parse("tel:$number")
//            startActivity(callIntent)
        }
        view.sickFood.setOnClickListener {
            Toast.makeText(context, "Your request has been sent", Toast.LENGTH_LONG).show()
        }
        view.contacts.setOnClickListener{
            startActivity(Intent(context, UserHostelContactsActivity::class.java))
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
         * @return A new instance of fragment userHostelFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            userHostelFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}