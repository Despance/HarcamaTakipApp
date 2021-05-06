package com.despance.harcamatakipapp.data

import com.despance.harcamatakipapp.api.RetrofitInstance
import retrofit2.Response

class ExpenseRepository (private val db : ExpenseDatabase){
    suspend fun insert(expense:Expense) = db.expenseDao().insert(expense)
    suspend fun delete(expense: Expense) = db.expenseDao().delete(expense)
    suspend fun loadById(expenseId: Int) = db.expenseDao().loadById(expenseId)
    suspend fun deleteAll() = db.expenseDao().deleteAll()
    suspend fun update(expense: Expense) = db.expenseDao().update(expense)
    suspend fun deleteById(expenseId:Int) = db.expenseDao().deleteById(expenseId)


    fun getAllExpenses() = db.expenseDao().getAll()


    suspend fun getCurrency(): Response<CurrencyModel>{
       return RetrofitInstance.api.getCurrency()
    }




}