package com.despance.harcamatakipapp.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "expense_table")
data class Expense (
    @ColumnInfo(name = "description")
    var description :String,
    @ColumnInfo(name = "expense_value")
    var expenseValue : String,
    @ColumnInfo(name = "expense_type")
    var expenseType : Int,
    @ColumnInfo(name = "currency")
    var currency : Int
    ){

    @PrimaryKey(autoGenerate = true)
    var id :Int? = null
}