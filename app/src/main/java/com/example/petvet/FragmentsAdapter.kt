package com.example.petvet
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class FragmentsAdapter (supportFragmentManager: FragmentManager): FragmentPagerAdapter(supportFragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT){
    private val mFragList = ArrayList<Fragment>()
    private val mFragTitle = ArrayList<String>()
    override fun getCount(): Int {
        return mFragList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mFragTitle[position]
    }
    fun addFrag(fragment: Fragment, title:String){
        mFragList.add(fragment)
        mFragTitle.add(title)
    }
}