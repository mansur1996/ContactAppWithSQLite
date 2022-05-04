package com.example.contactwithsql.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactwithsql.databinding.ActivityMainBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper

class MainActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = SQLiteHelper(this)
        initViews()

    }

    private fun initViews(){
        binding.btnCustomer.setOnClickListener {
            val intent = Intent(this, CustomerActivity::class.java)
            startActivity(intent)
        }

        binding.btnEmployee.setOnClickListener {
            val intent = Intent(this, EmployeeActivity::class.java)
            startActivity(intent)
        }

        binding.btnOrder.setOnClickListener {
            val intent = Intent(this, OrderActivity::class.java)
            startActivity(intent)
        }
    }

}