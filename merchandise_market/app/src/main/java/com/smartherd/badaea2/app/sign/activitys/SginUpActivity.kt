package com.smartherd.badaea2.app.sign.activitys

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.smartherd.badaea2.FragmentChangeListener

import com.smartherd.badaea2.R
import com.smartherd.badaea2.app.core.activitys.DashboardActivity
import com.smartherd.badaea2.addFragment
import com.smartherd.badaea2.app.sign.fragments.LoginFragment
import com.smartherd.badaea2.app.sign.fragments.Sign_upFragment
import com.smartherd.badaea2.removeFragment
import com.smartherd.badaea2.replaceFragment
import com.smartherd.badaea2.utils.preferences.PreferenceUsers

class SginUpActivity : AppCompatActivity(), FragmentChangeListener {
    private val sign_upFragment: Sign_upFragment = Sign_upFragment()
    private val loginFragment: LoginFragment = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sgin_up)


        chick()

    }

    private fun chick(){




        if (PreferenceUsers().isLogin(this)){

            val handler = Handler(Looper.getMainLooper())
            handler.postDelayed({
                reload()


                },0)

        }
        else{
            loadSignInFragment()
        }
    }


    override fun onFragmentChange(fragment: Fragment) {
        val ching = supportFragmentManager.beginTransaction()
        ching.replace(R.id.fragmentContainer, fragment)
        ching.commit()
    }
    fun loadSignInFragment() {
        if (loginFragment.isAdded) {
            replaceFragment(loginFragment, R.id.fragmentContainer)
            findViewById<FrameLayout>(R.id.fragmentContainer)
        } else {
            addFragment(loginFragment, R.id.fragmentContainer)
        }
    }

    private fun reload() {
        val muintin = Intent(this, DashboardActivity::class.java)
        startActivity(muintin)
        finish()
    }
    fun loadSignUpFragment() {
        if (sign_upFragment.isAdded) {
            replaceFragment(sign_upFragment, R.id.fragmentContainer)
            findViewById<FrameLayout>(R.id.fragmentContainer)
        } else {
            addFragment(sign_upFragment, R.id.fragmentContainer)
        }
    }


    override fun onBackPressed() {
        when {
            sign_upFragment.isVisible -> {
                removeFragment(sign_upFragment)
            }
            else -> super.onBackPressed()

        }
    }

}