package com.despance.harcamatakipapp.data

data class CurrencyModel (
    val success : Boolean,
    val timestamp:Int,
    val base : String,
    val date:String,
    val rates:HashMap<String,Any>,
    val error: HashMap<String,Any>
)