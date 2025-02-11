package com.example.newsnewshare.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.newsnewshare.ui.fragment.CategoryFragment
import com.example.newsnewshare.ui.fragment.NewsFragment
import com.example.newsnewshare.ui.fragment.ProfileFragment

class dashboardAdapter(fragmentManager: FragmentManager,lifecycle: Lifecycle):FragmentStateAdapter(fragmentManager, lifecycle){
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        when(position){

            0->return CategoryFragment()
            1->return NewsFragment()

            else->return CategoryFragment()
        }

    }

}