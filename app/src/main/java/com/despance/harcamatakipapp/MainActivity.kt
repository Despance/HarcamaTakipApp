package com.despance.harcamatakipapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.despance.harcamatakipapp.databinding.ActivityMainBinding
import com.despance.harcamatakipapp.expense.ExpenseViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ExpenseViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        /*
        val database  = ExpenseDatabase(this)
        val repository = ExpenseRepository(database)
        val factory = ExpenseViewModelFactory(repository)

        viewModel = ViewModelProvider(this,factory).get(ExpenseViewModel::class.java)


        val adapter = ExpenseRecyclerAdapter(listOf(),viewModel)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.adapter= adapter

        viewModel.getAllExpenses().observe(this, Observer {
            adapter.items = it
            adapter.notifyDataSetChanged()
        })*/




    }




}