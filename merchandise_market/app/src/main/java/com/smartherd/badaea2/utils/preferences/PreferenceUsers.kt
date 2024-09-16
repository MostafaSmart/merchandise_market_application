package com.smartherd.badaea2.utils.preferences

import android.content.Context
import android.os.Build
import android.view.Window
import android.view.WindowManager
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.models.User

class PreferenceUsers {

     fun changeStatusBarColor(color: String,window:Window) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window: Window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = android.graphics.Color.parseColor("#448AFF")
        }
    }
    fun isLogin(context: Context):Boolean{

        val preferencesHelper = PreferencesHelper(context)
        if (preferencesHelper.isLoggedIn) {

            return true

        } else {
            return false

        }

    }


    fun setUser(user: User,conf:Boolean,context: Context){

        val preferencesHelper = PreferencesHelper(context)

        preferencesHelper.isLoggedIn = true
        preferencesHelper.username = user.fName

        preferencesHelper.lname = user.lName

        preferencesHelper.email = user.email

        preferencesHelper.token = user.Token
        preferencesHelper.admin = user.admin
        if (conf){
            preferencesHelper.confe = "1"
        }
        else{
            preferencesHelper.confe ="0"
        }

        preferencesHelper.id = FirebaseAuth.getInstance().currentUser!!.uid

        preferencesHelper.type = user.type
        preferencesHelper.phone = user.phone
    }

    fun resetUserFile(user: User,conf:Boolean,context: Context){
        val preferencesHelper = PreferencesHelper(context)
        preferencesHelper.clear()
        setUser(user,conf,context)
    }


    fun getUser(context: Context): User?{
        val preferencesHelper = PreferencesHelper(context)
        if (preferencesHelper.isLoggedIn) {


            var user = User(preferencesHelper.username!!,preferencesHelper.lname!!,preferencesHelper.email!!,preferencesHelper.phone!!,preferencesHelper.type!!,preferencesHelper.token!!,preferencesHelper.admin!!)

            return user

        } else {

            return null

        }

    }

}