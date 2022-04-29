package com.example.contactwithsql.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.contactwithsql.R
import com.example.contactwithsql.databinding.ActivitySecondBinding
import com.example.contactwithsql.sqlite.helper.SQLiteHelper

class SecondActivity : AppCompatActivity() {

    private lateinit var myDbHelper : SQLiteHelper
    private lateinit var activitySecondBinding: ActivitySecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activitySecondBinding = ActivitySecondBinding.inflate(layoutInflater, null, false)
        setContentView(activitySecondBinding.root)
        myDbHelper = SQLiteHelper(this)
        initViews()
    }

    private fun initViews() {
        val id = intent.getIntExtra("id", 0)
        val contact = myDbHelper.getContactById(id)
        activitySecondBinding.tvName.text = contact.name
        activitySecondBinding.tvPhone.text = contact.phoneNumber
    }
}