package com.smartherd.badaea2.models

data class PresnttionWork(var userUid :String,var section:String,var subSections: HashMap<String,String>,var workeDesc:String,var img:ArrayList<String>){
    constructor():this("","", hashMapOf(), "",arrayListOf())

}
