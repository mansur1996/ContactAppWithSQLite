package com.example.contactwithsql.sqlite.helper

import com.example.contactwithsql.sqlite.model.Customer
import com.example.contactwithsql.sqlite.model.Employee
import com.example.contactwithsql.sqlite.model.Order

interface DatabaseService {

    fun insertCustomer(customer: Customer)
    fun insertEmployee(employee: Employee)
    fun insertOrder(order: Order)

    fun getAllCustomers() : List<Customer>
    fun getAllEmployees() : List<Employee>
    fun getAllOrders() : List<Order>
    fun getCustomerById(id: Int) : Customer
    fun getEmployeeById(id: Int) : Employee
}