package com.smartherd.badaea2.utils.preferences
import android.content.Context
import android.content.SharedPreferences
import com.smartherd.badaea2.models.User







class PreferencesHelper(context: Context) {

    private val sharedPreferences: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val KEY_IS_LOGGED_IN = "is_logged_in"
        private const val KEY_USERNAME = "fname"

        private const val KEY_LASTNAME = "lname"
        private const val KEY_EMAIL = "email"

        private const val KEY_PHONE = "phone"
        private const val KEY_TYPE = "type"
        private const val KEY_TOKEN = "token"

        private const val KEY_ADMIN = "admin"


        private const val KEY_CONF = "confe"

        private const val KEY_ID = "id"




    }

    var isLoggedIn: Boolean
        get() = sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
        set(value) = sharedPreferences.edit().putBoolean(KEY_IS_LOGGED_IN, value).apply()

    var username: String?
        get() = sharedPreferences.getString(KEY_USERNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_USERNAME, value).apply()

    var lname: String?
        get() = sharedPreferences.getString(KEY_LASTNAME, null)
        set(value) = sharedPreferences.edit().putString(KEY_LASTNAME, value).apply()


    var email: String?
        get() = sharedPreferences.getString(KEY_EMAIL, null)
        set(value) = sharedPreferences.edit().putString(KEY_EMAIL, value).apply()


    var phone: String?
        get() = sharedPreferences.getString(KEY_PHONE, null)
        set(value) = sharedPreferences.edit().putString(KEY_PHONE, value).apply()



    var type: String?
        get() = sharedPreferences.getString(KEY_TYPE, null)
        set(value) = sharedPreferences.edit().putString(KEY_TYPE, value).apply()

    var token: String?
        get() = sharedPreferences.getString(KEY_TOKEN, null)
        set(value) = sharedPreferences.edit().putString(KEY_TOKEN, value).apply()


    var admin: String?
        get() = sharedPreferences.getString(KEY_ADMIN, null)
        set(value) = sharedPreferences.edit().putString(KEY_ADMIN, value).apply()


    var id: String?
        get() = sharedPreferences.getString(KEY_ID, null)
        set(value) = sharedPreferences.edit().putString(KEY_ID, value).apply()


    var confe: String?
        get() = sharedPreferences.getString(KEY_CONF, null)
        set(value) = sharedPreferences.edit().putString(confe, value).apply()





    fun clear() {
        sharedPreferences.edit().clear().apply()
    }
}
