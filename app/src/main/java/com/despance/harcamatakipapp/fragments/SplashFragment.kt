package com.despance.harcamatakipapp.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.despance.harcamatakipapp.R


class SplashFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({

            if(onBoardingFinished()){
                val action =SplashFragmentDirections.actionSplashFragmentToMainFragment()
                findNavController().navigate(action)
            }else{
                val action = SplashFragmentDirections.actionSplashFragmentToViewPagerFragment()
                findNavController().navigate(action)
            }

                              },1000)



        return inflater.inflate(R.layout.fragment_splash, container, false)


    }

    private fun onBoardingFinished():Boolean{
        val sharedPref = requireActivity().getSharedPreferences("onBoarding", Context.MODE_PRIVATE)
        return sharedPref.getBoolean("Finished",false)
    }



}