package com.example.contactwithsql.sqlite.helper

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.contactwithsql.sqlite.model.Customer
import com.example.contactwithsql.sqlite.model.Employee
import com.example.contactwithsql.sqlite.model.Order

class SQLiteHelper(context: Context) :
    SQLiteOpenHelper(
        context,
        DATABASE_NAME,
        null,
        DATABASE_VERSION), DatabaseService {

    companion object {
        private const val DATABASE_NAME = "order.db"
        private const val DATABASE_VERSION = 1

        private const val CUSTOMER_TABLE = "customer"
        private const val CUSTOMER_ID = "id"
        private const val CUSTOMER_NAME = "name"
        private const val ADDRESS = "address"
        private const val POSTAL_CODE = "postal_code"
        private const val COUNTRY = "country"

        private const val EMPLOYEE_TABLE = "employees"
        private const val EMPLOYEE_ID = "id"
        private const val EMPLOYEE_NAME = "name"

        private const val ORDERS_TABLE = "orders"
        private const val ORDER_ID = "id"
        private const val CUSTOMER_ORDER_ID = "customer_id"
        private const val EMPLOYEE_ORDER_ID = "employee_id"
        private const val ORDER_DATE = "order_date"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val customerTableQuery = "CREATE TABLE $CUSTOMER_TABLE ($CUSTOMER_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $CUSTOMER_NAME TEXT NOT NULL, $ADDRESS TEXT NOT NULL, $POSTAL_CODE TEXT NOT NULL, $COUNTRY TEXT NOT NULL)"
        val employeeTableQuery = "CREATE TABLE $EMPLOYEE_TABLE ($EMPLOYEE_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $EMPLOYEE_NAME TEXT NOT NULL)"
        val ordersTableQuery = "CREATE TABLE $ORDERS_TABLE ($ORDER_ID INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT, $CUSTOMER_ORDER_ID INTEGER NOT NULL, $EMPLOYEE_ORDER_ID INTEGER NOT NULL, $ORDER_DATE TEXT NOT NULL, FOREIGN KEY($CUSTOMER_ORDER_ID) REFERENCES $CUSTOMER_TABLE ($CUSTOMER_ID), FOREIGN KEY($EMPLOYEE_ORDER_ID) REFERENCES $EMPLOYEE_TABLE ($EMPLOYEE_ID))"
        db?.execSQL(customerTableQuery)
        db?.execSQL(employeeTableQuery)
        db?.execSQL(ordersTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }

    override fun insertCustomer(customer: Customer) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(CUSTOMER_NAME, customer.name)
        contentValues.put(ADDRESS, customer.address)
        contentValues.put(POSTAL_CODE, customer.postalCode)
        contentValues.put(COUNTRY, customer.country)

        database.insert(CUSTOMER_TABLE, null, contentValues)
        database.close()
    }

    override fun insertEmployee(employee: Employee) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(EMPLOYEE_NAME, employee.name)

        database.insert(EMPLOYEE_TABLE, null, contentValues)
        database.close()
    }

    override fun insertOrder(order: Order) {
        val database = this.writableDatabase
        val contentValues = ContentValues()

        contentValues.put(CUSTOMER_ORDER_ID, order.customer?.id)
        contentValues.put(EMPLOYEE_ORDER_ID, order.employee?.id)
        contentValues.put(ORDER_DATE, order.orderDate)

        database.insert(ORDERS_TABLE, null, contentValues)
        database.close()
    }

    override fun getAllCustomers(): List<Customer> {
        val list = ArrayList<Customer>()
        val query = "select * from $CUSTOMER_TABLE"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if(cursor.moveToFirst()){
            do {
                val customer = Customer()
                customer.id = cursor.getInt(0)
                customer.name = cursor.getString(1)
                customer.address = cursor.getString(2)
                customer.postalCode = cursor.getString(3)
                customer.country = cursor.getString(4)
                list.add(customer)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllEmployees(): List<Employee> {
        val list = ArrayList<Employee>()
        val query = "select * from $EMPLOYEE_TABLE"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if(cursor.moveToFirst()){
            do {
                val employee = Employee()
                employee.id = cursor.getInt(0)
                employee.name = cursor.getString(1)
                list.add(employee)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getAllOrders(): ArrayList<Order> {
        val list = ArrayList<Order>()
        val query = "select * from $ORDERS_TABLE"
        val database = this.readableDatabase
        val cursor = database.rawQuery(query, null)

        if(cursor.moveToFirst()){
            do {
                val order = Order()
                order.id = cursor.getInt(0)
                order.customer = getCustomerById(cursor.getInt(1))
                order.employee = getEmployeeById(cursor.getInt(2))
                order.orderDate = cursor.getString(3)
                list.add(order)
            }while (cursor.moveToNext())
        }
        return list
    }

    override fun getCustomerById(id: Int): Customer {
        val database = this.readableDatabase
        val cursor = database.query(
            CUSTOMER_TABLE,
            arrayOf(CUSTOMER_ID,
                CUSTOMER_NAME,
                ADDRESS,
                POSTAL_CODE,
                COUNTRY),
            "$CUSTOMER_ID = ?",
            arrayOf(id.toString()),
        null,
        null,
        null
        )
        cursor?.moveToFirst()
        val customer = Customer()
        customer.id = cursor.getInt(0)
        customer.name = cursor.getString(1)
        customer.address = cursor.getString(2)
        customer.postalCode = cursor.getString(3)
        customer.country = cursor.getString(4)

        return customer
    }

    override fun getEmployeeById(id: Int): Employee {
        val database = this.readableDatabase
        val cursor = database.query(
            EMPLOYEE_TABLE,
            arrayOf(
                EMPLOYEE_ID,
                EMPLOYEE_NAME),
            "$EMPLOYEE_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )
        cursor?.moveToFirst()
        val employee = Employee()
        employee.id = cursor.getInt(0)
        employee.name = cursor.getString(1)

        return employee
    }

}