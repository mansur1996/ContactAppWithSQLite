package com.example.contactwithsql.activity

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.contactwithsql.R
import com.example.contactwithsql.adapter.ContactAdapter
import com.example.contactwithsql.databinding.ActivityMainBinding
import com.example.contactwithsql.databinding.MyDialogBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper
import com.example.contactwithsql.sqlite.model.Contact

class MainActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var adapter : ContactAdapter
    private lateinit var list : ArrayList<Contact>
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        myDbHelper = SQLiteHelper(this)
        initViews()

    }

    private fun initViews(){
        list= myDbHelper.getAllContacts()
        adapter = ContactAdapter(list, object : ContactAdapter.OnItemClickListener{
            override fun onItemClick(contact: Contact, position: Int, imageView: ImageView) {
                val popupMenu = PopupMenu(this@MainActivity, imageView)
                popupMenu.inflate(R.menu.popup_menu)

                popupMenu.setOnMenuItemClickListener { item ->
                    val itemId = item?.itemId

                    when (itemId) {
                        R.id.edit -> {
                            val dialog = AlertDialog.Builder(this@MainActivity)
                            val myDialogBinding =
                                MyDialogBinding.inflate(layoutInflater, null, false)

                            myDialogBinding.etName.setText(contact.name)
                            myDialogBinding.etPhone.setText(contact.phoneNumber)

                            dialog.setView(myDialogBinding.root)
                            dialog.setPositiveButton(
                                "Edit"
                            ) { dialog, which ->
                                val name = myDialogBinding.etName.text.toString().trim()
                                val phone = myDialogBinding.etPhone.text.toString().trim()

                                if (name.isNotEmpty() && phone.isNotEmpty()) {
                                    contact.name = name
                                    contact.phoneNumber = phone

                                    myDbHelper.updateContact(contact)
                                    list.set(position, contact)
                                    adapter.notifyItemChanged(position)

                                    Toast.makeText(
                                        this@MainActivity,
                                        "Editted Successfully",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                } else {
                                    Toast.makeText(
                                        this@MainActivity,
                                        "Editting field",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                            }

                            dialog.show()
                        }
                        R.id.delete -> {
                            myDbHelper.deleteContact(contact)
                            list.remove(contact)
                            adapter.notifyItemRemoved(list.size)
                            adapter.notifyItemRangeChanged(position, list.size)
                        }
                    }

                    true
                }

                popupMenu.show()
            }

            override fun onItemContactClick(contact: Contact) {
                val intent = Intent(this@MainActivity, SecondActivity::class.java)
                intent.putExtra("id", contact.id)
                startActivity(intent)
            }
        })
        binding.rvMain.adapter = adapter
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.my_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val itemId = item.itemId

        when (itemId){
            R.id.add -> {
                val dialog = AlertDialog.Builder(this)
                val myDialogBinding = MyDialogBinding.inflate(layoutInflater, null, false)
                dialog.setView(myDialogBinding.root)

                dialog.setPositiveButton("Save"
                ) { dialog, which ->
                    val name = myDialogBinding.etName.text.toString().trim()
                    val phone = myDialogBinding.etPhone.text.toString().trim()
                    if(name.isNotEmpty() && phone.isNotEmpty()){
                        val contact = Contact(name, phone)
                        myDbHelper.addContact(contact)
                        list.add(contact)
                        adapter.notifyItemInserted(list.size)
                        Toast.makeText(this, "Added Successfully", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this, "Adding field", Toast.LENGTH_SHORT).show()
                    }
                }
                dialog.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }

}