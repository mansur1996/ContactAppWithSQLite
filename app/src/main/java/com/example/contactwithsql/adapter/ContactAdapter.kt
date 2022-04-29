package com.example.contactwithsql.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.contactwithsql.databinding.ItemContactBinding
import com.example.contactwithsql.sqlite.model.Contact


class ContactAdapter(var list : List<Contact>, var onItemClickListener: OnItemClickListener) : RecyclerView.Adapter<ContactAdapter.VH>() {

    inner class VH(var itemContactBinding: ItemContactBinding) : RecyclerView.ViewHolder(itemContactBinding.root){

        fun onBind(contact: Contact, position: Int){
            itemContactBinding.tvName.text = contact.name
            itemContactBinding.tvPhone.text = contact.phoneNumber
            itemContactBinding.ivMore.setOnClickListener {
                onItemClickListener.onItemClick(contact, position, itemContactBinding.ivMore)
            }
            itemContactBinding.root.setOnClickListener {
                onItemClickListener.onItemContactClick(contact)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = ItemContactBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(itemView)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.onBind(list[position], position)
    }

    override fun getItemCount(): Int = list.size

    interface OnItemClickListener{
        fun onItemClick(contact: Contact, position: Int, imageView: ImageView)
        fun onItemContactClick(contact: Contact)
    }

}