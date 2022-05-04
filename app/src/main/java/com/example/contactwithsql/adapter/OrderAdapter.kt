package com.example.contactwithsql.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.contactwithsql.databinding.ItemOrderBinding
import com.example.contactwithsql.sqlite.model.Order


class OrderAdapter(var list : List<Order>) : RecyclerView.Adapter<OrderAdapter.VH>() {

    inner class VH(var itemOrderBinding: ItemOrderBinding) : RecyclerView.ViewHolder(itemOrderBinding.root){

        fun onBind(order: Order){
            itemOrderBinding.tvCustomer.text = order.customer?.name
            itemOrderBinding.tvEmployee.text = order.employee?.name
            itemOrderBinding.tvOrderDate.text = order.orderDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = ItemOrderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position])
    }

    override fun getItemCount(): Int = list.size
}