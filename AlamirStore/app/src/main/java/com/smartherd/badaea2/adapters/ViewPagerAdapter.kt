package com.smartherd.badaea2.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(private val fragments: List<Fragment>, lifecycle: Lifecycle, fragmentManager: FragmentManager) :
    FragmentStateAdapter(fragmentManager,lifecycle) {
    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment = fragments[position]


}