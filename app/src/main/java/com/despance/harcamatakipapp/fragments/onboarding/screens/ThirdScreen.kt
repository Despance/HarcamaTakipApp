package com.despance.harcamatakipapp.fragments.onboarding.screens

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.fragments.onboarding.ViewPagerFragmentDirections


class ThirdScreen : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_third_screen, container, false)

        view.findViewById<Button>(R.id.screen3_button).setOnClickListener {
            val action = ViewPagerFragmentDirections.actionViewPagerFragmentToNameFragment()
            findNavController().navigate(action)
            onBoardingFinished()

        }


        return view
    }

    private fun onBoardingFinished(){
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        val editor = sharedPref.edit()
        editor.putBoolean("Finished",true)
        editor.apply()
    }
}