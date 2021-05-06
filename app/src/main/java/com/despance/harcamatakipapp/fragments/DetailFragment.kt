package com.despance.harcamatakipapp.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.databinding.FragmentDetailBinding

class DetailFragment : Fragment() {

    private var _binding: FragmentDetailBinding?=null
    private val binding get() = _binding!!
    private var expenseId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View{
        _binding = FragmentDetailBinding.inflate(layoutInflater,container,false)

        arguments?.let {
            val bundle = DetailFragmentArgs.fromBundle(it)
            expenseId = bundle.expenseId
            binding.ExpenseIcon.setImageResource(bundle.expenseIcon)
            binding.expenseDescText.text = bundle.expenseDesc
            binding.expenseValueText.text = bundle.expenseValue

        }

        binding.deleteButton.setOnClickListener {
            val action = DetailFragmentDirections.actionDetailFragmentToMainFragment(deleteId = expenseId)
            Navigation.findNavController(it).navigate(action)
        }

        return binding.root
    }

}