package com.despance.harcamatakipapp.fragments

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.despance.harcamatakipapp.expense.ExpenseRecyclerAdapter
import com.despance.harcamatakipapp.expense.ExpenseViewModel
import com.despance.harcamatakipapp.expense.ExpenseViewModelFactory
import com.despance.harcamatakipapp.data.Expense
import com.despance.harcamatakipapp.data.ExpenseDatabase
import com.despance.harcamatakipapp.data.ExpenseRepository
import com.despance.harcamatakipapp.databinding.FragmentMainBinding
import com.despance.harcamatakipapp.util.Constants
import com.despance.harcamatakipapp.util.Constants.Companion.GENDER
import com.despance.harcamatakipapp.util.Constants.Companion.NAME
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.text.DecimalFormat

class MainFragment : Fragment() {


    private var _binding: FragmentMainBinding?=null
    private val binding get() = _binding!!
    private lateinit var viewModel: ExpenseViewModel
    private var name :String ?= null
    private var gender: String ? =null
    private var items:List<Expense> = listOf()

    private var currencyValue:HashMap<String,Any> = hashMapOf("GBP" to "1", "USD" to "1", "TRY" to "1")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        _binding = FragmentMainBinding.inflate(inflater,container,false)



        val database  = ExpenseDatabase(requireContext())
        val repository = ExpenseRepository(database)
        val factory = ExpenseViewModelFactory(repository)


        viewModel = ViewModelProvider(this,factory).get(ExpenseViewModel::class.java)
        val adapter = ExpenseRecyclerAdapter(listOf(),viewModel,viewModel.selectedCurrency,currencyValue,requireContext())

        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerView.adapter= adapter



        colorChange(viewModel.selectedCurrency)

        viewModel.getAllExpenses().observe(viewLifecycleOwner, Observer {
            adapter.items = it
            items = it
            adapter.notifyDataSetChanged()
        })

        arguments?.let {
            val bundle = MainFragmentArgs.fromBundle(it)

            if(bundle.deleteId != -1){
                viewModel.deleteById(bundle.deleteId)
                adapter.notifyDataSetChanged()
            }

            if(!bundle.description.isNullOrBlank() && !bundle.value.isNullOrBlank()){
                val desc = bundle.description
                val value = bundle.value
                val currency = bundle.currency
                val type = bundle.type



                viewModel.insert(Expense(desc,value,type,currency))



            }
        }
        loadNameData()


        binding.nameCardText.text = "Merhaba\n$name $gender "

        viewModel.getCurrency()

        viewModel.currencyValue.observe(viewLifecycleOwner, Observer {

            if(it.isSuccessful){
                Log.d("Response,", "Cache ${getCurrencyDataCache().toString()}")
                if(it.body()?.success.toString()=="true"){
                    Log.d("Response,",it.body()?.success.toString())
                    Log.d("Response,",it.body()?.timestamp.toString())
                    Log.d("Response,",it.body()?.base.toString())
                    Log.d("Response,",it.body()?.date.toString())
                    Log.d("Response,", it.body()?.rates.toString())

                    currencyValue = it.body()?.rates!!
                    adapter.currencyValue= currencyValue
                    adapter.notifyDataSetChanged()

                    saveCurrencyData(it.body()?.rates!!)
                    binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)

                }
                else if(it.body()?.success.toString()=="false"){
                    currencyValue = getCurrencyDataCache()
                    adapter.currencyValue = currencyValue
                    adapter.notifyDataSetChanged()
                    binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)

                    if(it.errorBody() != null){
                        Log.d("Response,",it.errorBody().toString())
                        val snackbar = Snackbar.make(requireView(),it.errorBody().toString(),Snackbar.LENGTH_SHORT)
                        snackbar.show()
                    }else{
                        Log.d("Response,", "You are offline")
                        val snackbar = Snackbar.make(requireView(),"Çevrim dışı moddasınız",Snackbar.LENGTH_SHORT)
                        snackbar.show()


                    }
                }


            }


        })

        binding.nameCardView.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToNameFragment()
            Navigation.findNavController(it).navigate(action)
        }

        binding.floatingActionButton.setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToAddFragment()
            Navigation.findNavController(it).navigate(action)

        }

        binding.TlButton.setOnClickListener {
            adapter.selectedCurrency = 1
            viewModel.selectedCurrency = 1
            adapter.notifyDataSetChanged()
            binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)
            colorChange(viewModel.selectedCurrency)


        }
        binding.sterlinButton.setOnClickListener {
            adapter.selectedCurrency = 4
            viewModel.selectedCurrency = 4
            adapter.notifyDataSetChanged()
            binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)
            colorChange(viewModel.selectedCurrency)
        }
        binding.euroButton.setOnClickListener {
            adapter.selectedCurrency = 3
            viewModel.selectedCurrency = 3
            adapter.notifyDataSetChanged()
            binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)
            colorChange(viewModel.selectedCurrency)
        }
        binding.dollarButton.setOnClickListener {
            adapter.selectedCurrency = 2
            viewModel.selectedCurrency = 2
            adapter.notifyDataSetChanged()
            binding.expenseCardText.text =getTotalValue(viewModel.selectedCurrency)
            colorChange(viewModel.selectedCurrency)
        }







        return binding.root




    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        this.arguments?.clear();
    }

    private fun loadNameData(){
        val sharedPrefs =activity?.getSharedPreferences(Constants.NAME_FRAGMENT_SHARED_PREF, Context.MODE_PRIVATE)
        name = sharedPrefs?.getString(NAME," ")
        when(sharedPrefs?.getInt(GENDER,3)){
            1-> gender = "Bey"
            2-> gender = "Hanım"
            3-> gender = " "

        }

    }

    private fun saveCurrencyData(hashMap: HashMap<String,Any>){
        val sharedPrefs = activity?.getSharedPreferences(Constants.CURRENCY_DATA,Context.MODE_PRIVATE)

        val gson = Gson()
        val json = gson.toJson(hashMap)
        sharedPrefs?.edit()?.putString("currencyData",json)?.apply()

    }

    private fun getCurrencyDataCache():HashMap<String, Any>{
        val sharedPrefs = activity?.getSharedPreferences(Constants.CURRENCY_DATA,Context.MODE_PRIVATE)
        val gson = Gson()
        val json = sharedPrefs?.getString("currencyData",currencyValue.toString())
        val type = object : TypeToken<HashMap<String,Any>>() {}.type

        return gson.fromJson(json,type)
    }

    private fun getTotalValue(selectedCurrency : Int) : String{

        var totalValue = 0.0
        var sign = ""
        val gbp : Double = currencyValue["GBP"].toString().toDouble()
        val usd : Double= currencyValue["USD"].toString().toDouble()
        val tl :Double = currencyValue["TRY"].toString().toDouble()

        when(selectedCurrency){
            1-> sign = "TL"
            2-> sign = "$"
            3-> sign = "€"
            4-> sign = "£"
        }

        items.forEach {
            var displayedValue = 0.0
            val value = it.expenseValue.toDouble()
            if(it.currency == 1){
                when(selectedCurrency){
                    1-> displayedValue = value
                    2->displayedValue = value*(usd/tl)
                    3->displayedValue = value/tl
                    4->displayedValue = value*(gbp/tl)
                }
            }else if(it.currency == 2){
                when(selectedCurrency){
                    1->displayedValue = value*(tl/usd)
                    2->displayedValue = value
                    3->displayedValue = value/(usd)
                    4->displayedValue = value*(gbp/usd)
                }
            }else if ( it.currency == 3){
                when(selectedCurrency){
                    1->displayedValue = value*tl
                    2->displayedValue = value*usd
                    3->displayedValue = value
                    4->displayedValue =value*gbp
                }
            }else if (it.currency == 4){
                when(selectedCurrency){
                    1->displayedValue = value*(tl/gbp)
                    2->displayedValue = value*(usd/gbp)
                    3->displayedValue = value/(gbp)
                    4->displayedValue = value
                }
            }
            totalValue+=displayedValue


        }


        return "Harcamanız\n${DecimalFormat("#.##").format(totalValue)}$sign"
    }

    fun colorChange(selectedCurrency: Int){
        val selectedColor = Color.parseColor("#f7b100")
        val notSelectedColor = Color.GRAY
        if(selectedCurrency == 1){
            binding.TlButton.setTextColor(selectedColor)
            binding.euroButton.setTextColor(notSelectedColor)
            binding.dollarButton.setTextColor(notSelectedColor)
            binding.sterlinButton.setTextColor(notSelectedColor)

            binding.TlButton.strokeColor = ColorStateList.valueOf(selectedColor)
            binding.euroButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.dollarButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.sterlinButton.strokeColor = ColorStateList.valueOf(notSelectedColor)

        }else if(selectedCurrency == 4){
            binding.TlButton.setTextColor(notSelectedColor)
            binding.euroButton.setTextColor(notSelectedColor)
            binding.dollarButton.setTextColor(notSelectedColor)
            binding.sterlinButton.setTextColor(selectedColor)

            binding.TlButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.euroButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.dollarButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.sterlinButton.strokeColor = ColorStateList.valueOf(selectedColor)
        }else if (selectedCurrency == 3){
            binding.TlButton.setTextColor(notSelectedColor)
            binding.euroButton.setTextColor(selectedColor)
            binding.dollarButton.setTextColor(notSelectedColor)
            binding.sterlinButton.setTextColor(notSelectedColor)

            binding.TlButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.euroButton.strokeColor = ColorStateList.valueOf(selectedColor)
            binding.dollarButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.sterlinButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
        }else if (selectedCurrency == 2){
            binding.TlButton.setTextColor(notSelectedColor)
            binding.euroButton.setTextColor(notSelectedColor)
            binding.dollarButton.setTextColor(selectedColor)
            binding.sterlinButton.setTextColor(notSelectedColor)

            binding.TlButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.euroButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
            binding.dollarButton.strokeColor = ColorStateList.valueOf(selectedColor)
            binding.sterlinButton.strokeColor = ColorStateList.valueOf(notSelectedColor)
        }

    }

}