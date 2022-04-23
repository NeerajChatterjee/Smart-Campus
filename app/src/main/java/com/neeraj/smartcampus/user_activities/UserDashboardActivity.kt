package com.neeraj.smartcampus.user_activities

import android.app.Fragment
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ismaeldivita.chipnavigation.ChipNavigationBar
import com.neeraj.smartcampus.R
import com.neeraj.smartcampus.interfaces.userDashboardInterface
import com.neeraj.smartcampus.user_activities.fragments.userFoodFragment
import com.neeraj.smartcampus.user_activities.fragments.userHostelFragment
import com.neeraj.smartcampus.user_activities.fragments.userProfileFragment

@Suppress("DEPRECATION")
class UserDashboardActivity : AppCompatActivity() {

    lateinit var bottomNav: ChipNavigationBar
    lateinit var listener: userDashboardInterface

    companion object {
        const val EXTRA_FRAGMENT = "name_extra"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_dashboard)

        bottomNav = findViewById(R.id.bottom_nav)

        var frag = intent.getStringExtra(UserDashboardActivity.EXTRA_FRAGMENT)

        if(frag == "1"){
            bottomNav.setItemSelected(R.id.food, true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userFoodFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Food"
        }
        else if(frag == "2"){
            bottomNav.setItemSelected(R.id.profile,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userProfileFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Profile"
        }
        // By default the hostel page should be selected on opening the app
        else if(savedInstanceState==null){
            bottomNav.setItemSelected(R.id.hostel,true)
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userHostelFragment()).commitAllowingStateLoss()
            supportActionBar!!.title = "Hostel"
        }

        // Listener on the bottomNav, and selecting the fragment according to their ids
        bottomNav.setOnItemSelectedListener {

            var fragment: Fragment? = null

            when(it){
                R.id.hostel ->{
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userHostelFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Hostel"
                }
                R.id.food -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userFoodFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Food"
                }
                R.id.profile -> {
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, userProfileFragment()).commitAllowingStateLoss()
                    supportActionBar!!.title = "Profile"
                }
            }


        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(a)
    }

}