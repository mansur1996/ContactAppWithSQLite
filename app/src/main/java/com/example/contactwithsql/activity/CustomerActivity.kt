package com.example.contactwithsql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactwithsql.databinding.ActivityCustomerBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper
import com.example.contactwithsql.sqlite.model.Customer

class CustomerActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var binding: ActivityCustomerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        myDbHelper = SQLiteHelper(this)
        initViews()
    }

    private fun initViews() {
        binding.btnSave.setOnClickListener {
            val name = binding.etName.text.toString().trim()
            val address = binding.etAddress.text.toString().trim()
            val postalCode = binding.etPostalCode.text.toString().trim()
            val country = binding.etCountry.text.toString().trim()

            val customer = Customer(name, address, postalCode, country)
            myDbHelper.insertCustomer(customer)
            Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}