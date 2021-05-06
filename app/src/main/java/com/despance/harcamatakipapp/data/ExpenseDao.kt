package com.despance.harcamatakipapp.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ExpenseDao {
    @Query("SELECT * FROM expense_table")
    fun getAll():LiveData<List<Expense>>

    @Query("SELECT * FROM expense_table WHERE id IN (:expenseId)")
    suspend fun loadById(expenseId:Int):Expense?


    @Query("DELETE FROM expense_table WHERE id IN (:expenseId)")
    suspend fun deleteById(expenseId:Int)

    @Insert
    suspend fun insert(expense: Expense)

    @Update
    suspend fun update(expense: Expense)

    @Delete
    suspend fun delete(expense: Expense)

    @Query("DELETE FROM expense_table")
    suspend fun deleteAll()


}