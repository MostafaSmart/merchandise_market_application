package com.smartherd.badaea2.models

import android.os.Parcelable

import java.util.Date

data class Rating(var userRatUid:String,var rating:String,var comment:String,var date: Date){
    constructor():this("","","", Date())
}



