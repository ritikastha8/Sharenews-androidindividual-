package com.example.newsnewshare.ui.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.newsnewshare.R
import com.example.newsnewshare.adapter.dashboardAdapter
import com.example.newsnewshare.databinding.ActivityDashboardBinding
import com.example.newsnewshare.ui.fragment.CategoryFragment
import com.example.newsnewshare.ui.fragment.NewsFragment
import com.example.newsnewshare.ui.fragment.ProfileFragment

class DashboardActivity : AppCompatActivity() {
    lateinit var dashboardBinding: ActivityDashboardBinding
//    lateinit var adapter: dashboardAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        dashboardBinding = ActivityDashboardBinding.inflate(layoutInflater)
        setContentView(dashboardBinding.root)

        replaceFragment(CategoryFragment())

        dashboardBinding.bottomNavigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.bottom_category -> replaceFragment(CategoryFragment())
                R.id.bottom_news -> replaceFragment(NewsFragment())


                else -> {}
            }
            true

        }


        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.outputadmin)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }




    private fun replaceFragment(Fragment: Fragment) {

        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(dashboardBinding.frameLayout.id, Fragment)
        fragmentTransaction.commit()
    }
}








//        adapter = dashboardAdapter(supportFragmentManager, lifecycle)
//        dashboardBinding.viewPager.adapter = adapter
//
//
//        TabLayoutMediator(
//            dashboardBinding.tablayoutt,
//            dashboardBinding.viewPager
//        ) { tabs, position ->
//            tabs.icon = resources.getDrawable(icons[position], null)
//        }.attach()



