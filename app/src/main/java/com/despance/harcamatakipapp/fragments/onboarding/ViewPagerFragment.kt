package com.despance.harcamatakipapp.fragments.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager2.widget.ViewPager2
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.fragments.onboarding.screens.FirstScreen
import com.despance.harcamatakipapp.fragments.onboarding.screens.SecondScreen
import com.despance.harcamatakipapp.fragments.onboarding.screens.ThirdScreen


class ViewPagerFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_view_pager, container, false)

        val fragmentList = arrayListOf<Fragment>(
            FirstScreen(),
            SecondScreen(),
            ThirdScreen()
        )



        val adapter = ViewPagerAdapter(
            fragmentList,requireActivity().supportFragmentManager,lifecycle
        )
        view.findViewById<ViewPager2>(R.id.view_pager).adapter = adapter

        return view
    }


}