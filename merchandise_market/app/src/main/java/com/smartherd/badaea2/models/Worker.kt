package com.smartherd.badaea2.models

data class Worker(var userUid:String,var imgProfile:String,var section:String,var subSections: HashMap<String,String>,var workeDesc:String ){
    constructor():this("","","", hashMapOf(),"")
}
