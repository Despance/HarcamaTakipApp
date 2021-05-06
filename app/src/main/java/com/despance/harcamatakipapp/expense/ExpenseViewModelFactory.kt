package com.despance.harcamatakipapp.expense

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.despance.harcamatakipapp.data.ExpenseRepository

class ExpenseViewModelFactory(private val repository: ExpenseRepository):ViewModelProvider.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ExpenseViewModel(repository) as T
    }
}