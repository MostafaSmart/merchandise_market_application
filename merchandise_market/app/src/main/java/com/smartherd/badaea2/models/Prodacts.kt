package com.smartherd.badaea2.models

import java.util.Date

data class Prodacts(var uid:String, var workerUid:String,var mainSections:String,var subSections: ArrayList<String>,var name:String,var descrip:String,var imgs:ArrayList<String>,var sizePrice:HashMap<String,String>,var sizeDiscount:HashMap<String,String>,var colors:ArrayList<String>,var count:String,var date: Date){
    constructor():this("","","",
        arrayListOf(),"","", arrayListOf(), hashMapOf(),hashMapOf(), arrayListOf(),"",Date())
}
