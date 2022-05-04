package com.example.contactwithsql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactwithsql.databinding.ActivityEmployeeBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper
import com.example.contactwithsql.sqlite.model.Employee

class EmployeeActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var binding: ActivityEmployeeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEmployeeBinding.inflate(layoutInflater, null, false)
        setContentView(binding.root)

        myDbHelper = SQLiteHelper(this)
        initViews()
    }

    private fun initViews() {
       binding.btnSave.setOnClickListener {
           val name = binding.etName.text.toString().trim()
           val employee = Employee(name)

           myDbHelper.insertEmployee(employee)
           Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
           finish()
       }
    }
}