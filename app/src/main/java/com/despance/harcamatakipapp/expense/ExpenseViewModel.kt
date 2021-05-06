package com.despance.harcamatakipapp.expense

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.despance.harcamatakipapp.data.CurrencyModel
import com.despance.harcamatakipapp.data.Expense
import com.despance.harcamatakipapp.data.ExpenseRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response

class ExpenseViewModel(private val expenseRepository: ExpenseRepository) : ViewModel() {

    val currencyValue : MutableLiveData<Response<CurrencyModel>> = MutableLiveData()

    fun insert(expense: Expense) = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.insert(expense)
    }
    fun delete(expense: Expense) = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.delete(expense)
    }
    fun update(expense: Expense) = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.update(expense)
    }
    fun loadById(expenseId: Int) = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.loadById(expenseId)
    }fun deleteAll() = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.deleteAll()
    }
    fun deleteById(expenseId: Int) = CoroutineScope(Dispatchers.Main).launch {
        expenseRepository.deleteById(expenseId)
    }
    fun getAllExpenses() = expenseRepository.getAllExpenses()

    var selectedCurrency = 1


    fun getCurrency(){
        viewModelScope.launch {
            val currency :Response<CurrencyModel>  = expenseRepository.getCurrency()
            currencyValue.value = currency
        }
    }




}