package com.despance.harcamatakipapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.databinding.FragmentAddBinding
import com.despance.harcamatakipapp.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class AddFragment : Fragment() {

    private var _binding: FragmentAddBinding?=null
    private val binding get() = _binding!!
    var currency = -1
    var type = -1
    var desc = ""
    var value = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentAddBinding.inflate(inflater,container,false)

        binding.button.setOnClickListener {



            when(binding.currencyRadioGroup.checkedRadioButtonId){
                R.id.radioButton11-> currency=1
                R.id.radioButton10->currency =2
                R.id.radioButton8->currency=3
                R.id.radioButton9->currency=4
            }
            when(binding.typeRadioGroup.checkedRadioButtonId){
                R.id.radioButton7->type = 1
                R.id.radioButton6->type = 2
                R.id.radioButton5->type = 3
            }
            desc = binding.descriptionEditTextView.editText?.text.toString()
            value = binding.valueEditTextView.editText?.text.toString()


            if(currency != -1 && type != -1 && desc != "" && value != "" && value.toDoubleOrNull() != null){

                val action = AddFragmentDirections.actionAddFragmentToMainFragment(desc,value, currency, type)
                Navigation.findNavController(requireView()).navigate(action)

            }else{
                showSnackBar()
            }

        }






        return binding.root
    }

    fun showSnackBar(){
        val snack = Snackbar.make(requireView(),"Lütfen tüm alanı doldurunuz!",Snackbar.LENGTH_SHORT)
        snack.show()
    }

}