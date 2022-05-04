package com.example.contactwithsql.sqlite.model

class Employee {
    var id : Int? = null
    var name : String? = null

    constructor(name: String?) {
        this.name = name
    }

    constructor()
}