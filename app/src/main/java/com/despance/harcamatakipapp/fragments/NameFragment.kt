package com.despance.harcamatakipapp.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.databinding.FragmentNameBinding
import com.despance.harcamatakipapp.util.Constants.Companion.GENDER
import com.despance.harcamatakipapp.util.Constants.Companion.NAME
import com.despance.harcamatakipapp.util.Constants.Companion.NAME_FRAGMENT_SHARED_PREF
import com.google.android.material.snackbar.Snackbar


class NameFragment : Fragment() {

    private var _binding  : FragmentNameBinding?= null
    private val binding get() = _binding!!

    var username = " "
    var gender = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {

        _binding = FragmentNameBinding.inflate(inflater,container,false)

        binding.button2.setOnClickListener {

            val sharedPref = activity?.getSharedPreferences(NAME_FRAGMENT_SHARED_PREF, Context.MODE_PRIVATE)

            username = binding.editTextTextPersonName.editText?.text.toString()

            when(binding.radioGroup.checkedRadioButtonId){
                R.id.radioButton3->gender = 1
                R.id.radioButton2->gender = 2
                R.id.radioButton->gender = 3
            }

            if(username != " " && username.isNotBlank() && gender != 0){
                sharedPref?.edit()?.putString(NAME,username)?.apply()
                sharedPref?.edit()?.putInt(GENDER,gender)?.apply()

                val action = NameFragmentDirections.actionNameFragmentToMainFragment()
                Navigation.findNavController(it).navigate(action)
            }else{
                showSnackBar()
            }




        }







        return binding.root
    }

    fun showSnackBar(){
        val snack = Snackbar.make(requireView(),"Lütfen tüm alanı doldurunuz!", Snackbar.LENGTH_SHORT)
        snack.show()
    }


}
