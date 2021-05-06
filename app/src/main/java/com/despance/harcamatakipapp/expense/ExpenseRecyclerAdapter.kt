package com.despance.harcamatakipapp.expense

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.despance.harcamatakipapp.R
import com.despance.harcamatakipapp.data.Expense
import com.despance.harcamatakipapp.fragments.MainFragmentDirections
import java.text.DecimalFormat
import kotlin.math.roundToInt

class ExpenseRecyclerAdapter(var items: List<Expense>, private val viewModel: ExpenseViewModel, var selectedCurrency : Int, var currencyValue: HashMap<String,Any>, context:Context) : RecyclerView.Adapter<ExpenseRecyclerAdapter.ExpenseViewHolder>(){





    inner class ExpenseViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ExpenseViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.expense_row,parent,false)
        return ExpenseViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ExpenseViewHolder, position: Int) {
        var displayedValue = 0.0
        val currentExpenseItem = items[position]
        val gbp : Double = currencyValue["GBP"].toString().toDouble()
        val usd : Double= currencyValue["USD"].toString().toDouble()
        val tl :Double = currencyValue["TRY"].toString().toDouble()

        holder.itemView.findViewById<TextView>(R.id.descriptionText).text = currentExpenseItem.description
        var sign = " "
        val iconView = holder.itemView.findViewById<ImageView>(R.id.icon_image)
        var expenseIcon = 0
        when(currentExpenseItem.expenseType){
            1->expenseIcon = R.drawable.ic_receipt
            2-> expenseIcon = R.drawable.ic_house
            3-> expenseIcon = R.drawable.ic_shopping_bag
        }
        iconView.setImageResource(expenseIcon)
        when(selectedCurrency){
            1-> sign = "TL"
            2-> sign = "$"
            3-> sign = "€"
            4-> sign = "£"
        }

        var value = currentExpenseItem.expenseValue.toDouble()
        if(currentExpenseItem.currency == 1){
            when(selectedCurrency){
                1-> displayedValue = value
                2->displayedValue = value*(usd/tl)
                3->displayedValue = value/tl
                4->displayedValue = value*(gbp/tl)
            }
        }else if(currentExpenseItem.currency == 2){
            when(selectedCurrency){
                1->displayedValue = value*(tl/usd)
                2->displayedValue = value
                3->displayedValue = value/(usd)
                4->displayedValue = value*(gbp/usd)
            }
        }else if ( currentExpenseItem.currency == 3){
            when(selectedCurrency){
                1->displayedValue = value*tl
                2->displayedValue = value*usd
                3->displayedValue = value
                4->displayedValue =value*gbp
            }
        }else if (currentExpenseItem.currency == 4){
            when(selectedCurrency){
                1->displayedValue = value*(tl/gbp)
                2->displayedValue = value*(usd/gbp)
                3->displayedValue = value/(gbp)
                4->displayedValue = value
            }
        }




        val lastValue = if(displayedValue>=10.0){
            "${displayedValue.roundToInt()}"
        }else{
            DecimalFormat("#.##").format(displayedValue)
        }

        holder.itemView.findViewById<TextView>(R.id.valueText).text = "$lastValue$sign"


        holder.itemView.findViewById<CardView>(R.id.expenseCardView).setOnClickListener {
            val action = MainFragmentDirections.actionMainFragmentToDetailFragment(currentExpenseItem.id!!,currentExpenseItem.description,"${DecimalFormat("#.##").format(displayedValue)} $sign",currentExpenseItem.currency,expenseIcon)

            Navigation.findNavController(it).navigate(action)

        }




    }



    override fun getItemCount(): Int {
       return items.size
    }



}