package com.example.contactwithsql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.contactwithsql.R
import com.example.contactwithsql.adapter.CustomerAdapter
import com.example.contactwithsql.adapter.EmployeeAdapter
import com.example.contactwithsql.adapter.OrderAdapter
import com.example.contactwithsql.databinding.ActivityCustomerBinding
import com.example.contactwithsql.databinding.ActivityOrderBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper
import com.example.contactwithsql.sqlite.model.Customer
import com.example.contactwithsql.sqlite.model.Employee
import com.example.contactwithsql.sqlite.model.Order

class OrderActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var binding: ActivityOrderBinding
    private lateinit var customerAdapter: CustomerAdapter
    private lateinit var employeeAdapter: EmployeeAdapter
    private lateinit var orderAdapter: OrderAdapter
    private lateinit var listCustomers: List<Customer>
    private lateinit var listEmployees: List<Employee>
    private lateinit var listOrders: ArrayList<Order>

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityOrderBinding.inflate(layoutInflater, null, false)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        myDbHelper = SQLiteHelper(this)
        listCustomers = myDbHelper.getAllCustomers()
        listEmployees = myDbHelper.getAllEmployees()
        listOrders = myDbHelper.getAllOrders()
        initViews()
    }

    private fun initViews() {
        customerAdapter = CustomerAdapter(listCustomers)
        binding.customerSpinner.adapter = customerAdapter

        employeeAdapter = EmployeeAdapter(listEmployees)
        binding.employeeSpinner.adapter = employeeAdapter

        orderAdapter = OrderAdapter(listOrders)
        binding.rvOrder.adapter = orderAdapter

        binding.btnSave.setOnClickListener { saveOrder() }


    }

    private fun saveOrder(){
        val customer = listCustomers[binding.customerSpinner.selectedItemPosition]
        val employee = listEmployees[binding.employeeSpinner.selectedItemPosition]
        val orderDate = binding.etOrderDate.text.toString().trim()
        val order = Order(customer, employee, orderDate)
        myDbHelper.insertOrder(order)
        Toast.makeText(this, "Successfully added", Toast.LENGTH_SHORT).show()
        listOrders.add(order)
        orderAdapter.notifyItemInserted(listOrders.size)
    }
}