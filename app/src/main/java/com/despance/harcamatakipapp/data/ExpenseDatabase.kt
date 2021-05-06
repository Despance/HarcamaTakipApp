package com.despance.harcamatakipapp.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Expense::class],version = 1)
abstract class ExpenseDatabase :RoomDatabase() {
    abstract fun expenseDao():ExpenseDao

    companion object{
        @Volatile
        private var instance: ExpenseDatabase? = null
        private val LOCK = Any()
        operator fun invoke(context: Context) =
            instance?: synchronized(LOCK){
                instance?: createDatabase(context).also { instance = it }

        }

        private fun createDatabase(context: Context) = Room.databaseBuilder(context.applicationContext,ExpenseDatabase::class.java,"expense_database").build()


    }
}