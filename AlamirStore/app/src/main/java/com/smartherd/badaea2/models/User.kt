package com.smartherd.badaea2.models

data class User(var fName:String,var lName:String , var email:String,var phone:String,var type:String,var Token:String,var admin:String){
    constructor() : this("", "", "", "","","","")
}
