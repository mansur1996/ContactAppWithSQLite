package com.example.contactwithsql.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import com.example.contactwithsql.R
import com.example.contactwithsql.databinding.ItemCustomerBinding
import com.example.contactwithsql.databinding.ItemEmployeeBinding
import com.example.contactwithsql.sqlite.model.Customer
import com.example.contactwithsql.sqlite.model.Employee

class EmployeeAdapter(var list : List<Employee>) : BaseAdapter() {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): Employee {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var employeeViewHolder : EmployeeViewHolder
        if(convertView == null){
            val binding = ItemEmployeeBinding.inflate(LayoutInflater.from(parent?.context), parent, false)
            employeeViewHolder = EmployeeViewHolder(binding)
        }else{
            employeeViewHolder = EmployeeViewHolder(ItemEmployeeBinding.bind(convertView))
        }
        employeeViewHolder.itemEmployeeBinding.tvEmployee.text = list[position].name

        return employeeViewHolder.itemView
    }

    inner class EmployeeViewHolder{
        val itemView : View
        val itemEmployeeBinding : ItemEmployeeBinding

        constructor(itemEmployeeBinding: ItemEmployeeBinding){
            itemView = itemEmployeeBinding.root
            this.itemEmployeeBinding = itemEmployeeBinding
        }
    }

}