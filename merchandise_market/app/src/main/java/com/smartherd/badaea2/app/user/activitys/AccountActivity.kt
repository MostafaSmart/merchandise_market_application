package com.smartherd.badaea2.app.user.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth

import com.smartherd.badaea2.R
import com.smartherd.badaea2.app.worker.activitys.GetWorkActivity
import com.smartherd.badaea2.app.worker.activitys.WPActivity
import com.smartherd.badaea2.app.sign.activitys.SginUpActivity
import com.smartherd.badaea2.app.worker.activitys.WorkerDashbordActivity
import com.smartherd.badaea2.models.User
import com.smartherd.badaea2.servers.users.UserServes
import com.smartherd.badaea2.utils.preferences.PreferenceUsers
import com.smartherd.badaea2.utils.preferences.PreferencesHelper

class AccountActivity : AppCompatActivity() {
    private lateinit var tvOffer: TextView
    private lateinit var edtPhone: EditText
    private lateinit var tvWorker: TextView
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    private lateinit var btnSavephone: Button
    private lateinit var tvWishlist: TextView
    private lateinit var tvQuickPay: TextView
    private lateinit var btnConfimEmail: Button
    private lateinit var tvHelpCenter: TextView
    private lateinit var prog_acount: ProgressBar
    private lateinit var txtDisplayName: TextView
    private lateinit var rlContent: RelativeLayout
    private lateinit var tvVerifyHeading: TextView
    private lateinit var tvAddressManager: TextView
    private lateinit var tvVerifySubHeading: TextView
    private lateinit var R_EmailConfirm: RelativeLayout
    private lateinit var banner_container: LinearLayout
    private lateinit var profile_image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var btnSignOut: com.google.android.material.button.MaterialButton

    private lateinit var toolbar_end: ImageView
    private lateinit var phone_contenrer:RelativeLayout
    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_account)


        extracted()

        var user = showUserData()

        btnSignOut.setOnClickListener {
            logOut()

        }


        tvAddressManager.setOnClickListener {
//            var int = Intent(this, WPActivity::class.java)
//            startActivity(int)
        }
        tvWorker.setOnClickListener {

            if (user!=null)
            {
                if(user.type != "c"){
                    var int = Intent(this, WorkerDashbordActivity::class.java)
                    int.putExtra("userName",user.fName+" " +user.lName)
                    startActivity(int)
                }
                else{
                    if(UserServes().chickUserConfremdEmail()){
                        var int = Intent(this, GetWorkActivity::class.java)
                        startActivity(int)
                    }
                    else{
                        tvVerifySubHeading.visibility = View.VISIBLE
                        tvVerifySubHeading.text = "يجب القيام بتأكيد البريد الالكتورني اولاً "
                        Snackbar.make(tvVerifySubHeading,"يجب القيام بتأكيد بريدك الالكتروني اولاً",Toast.LENGTH_SHORT).show()
                    }
                }


            }


        }

        btnConfimEmail.setOnClickListener {

            prog_acount.visibility = View.VISIBLE
            UserServes().confermUserEmail {
                if (it == "done"){
                    Snackbar.make(btnConfimEmail,"قم بمراجعة بريدك الالكتروني لاكمال عملية التحقق",Snackbar.LENGTH_SHORT).show()
                    tvVerifySubHeading.visibility = View.VISIBLE
                    tvVerifySubHeading.text = "تم ارسال رمز التحقق الى بريدك الالكتروني "
                    prog_acount.visibility = View.GONE
                }
                else{
                    Snackbar.make(btnConfimEmail,"حدث خطاء, حاول لاحقاً",Snackbar.LENGTH_SHORT).show()
                    prog_acount.visibility = View.GONE
                }
            }

        }

        btnSavephone.setOnClickListener {
            if(edtPhone.text.isNotEmpty() && edtPhone.text.count() == 9){
                updatePhone()


            }
            else{
                edtPhone.setError("مطلوب ادخال الرقم")
                edtPhone.requestFocus()
            }
        }

    }

    private fun logOut() {
        val preferencesHelper = PreferencesHelper(this)
        preferencesHelper.clear()
        FirebaseAuth.getInstance().signOut()
        reload()
    }

    private fun updatePhone() {
        prog_acount.visibility = View.VISIBLE

        var user = PreferenceUsers().getUser(this)
        if (user!=null){
            user.phone = edtPhone.text.toString()
            UserServes().updateUser(user) { msg ->
                if (msg == "done") {
                    PreferenceUsers().resetUserFile(user, FirebaseAuth.getInstance().currentUser!!.isEmailVerified, this)
                    prog_acount.visibility = View.GONE
                    Snackbar.make(btnSavephone, "تم العميلة بنجاح ! ", Snackbar.LENGTH_SHORT).show()
                }
                else {
                    prog_acount.visibility = View.GONE
                    Snackbar.make(btnSavephone, "حدث خطاء", Snackbar.LENGTH_SHORT).show()
                }

            }
        }
    }

    private fun showUserData():User? {
        if(PreferenceUsers().isLogin(this)){


            var user = PreferenceUsers().getUser(this)

            if(user!=null){

                Toast.makeText(this,user.phone,Toast.LENGTH_SHORT).show()
                txtDisplayName.text = user.fName + " " + user.lName

                if (UserServes().chickUserConfremdEmail()){

                    R_EmailConfirm.visibility = View.GONE
                }

                if (user.phone!="0"){
                    tvVerifySubHeading.visibility = View.GONE
                    phone_contenrer.visibility = View.GONE
                }





                return user
            }
            else{
                return null
            }

        }
        else{
            return null
        }
    }


    private fun reload() {
        val muintin = Intent(this, SginUpActivity::class.java)
        muintin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(muintin)
    }

    private fun extracted() {


        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)




        tvOffer = findViewById(R.id.tvOffer)
        edtPhone = findViewById(R.id.edtPhone)
        tvWorker = findViewById(R.id.tvWorker)
        rlContent = findViewById(R.id.rlContent)
        tvWishlist = findViewById(R.id.tvWishlist)
        tvQuickPay = findViewById(R.id.tvQuickPay)
        btnSignOut = findViewById(R.id.btnSignOut)
        prog_acount = findViewById(R.id.prog_acount)
        toolbar = findViewById(R.id.toolbar)
        btnSavephone = findViewById(R.id.btnSavephone)
        tvHelpCenter = findViewById(R.id.tvHelpCenter)
        profile_image = findViewById(R.id.profile_image)
        txtDisplayName = findViewById(R.id.txtDisplayName)
        R_EmailConfirm = findViewById(R.id.R_EmailConfirm)
        btnConfimEmail = findViewById(R.id.btnConfimEmail)
        tvVerifyHeading = findViewById(R.id.tvVerifyHeading)
        tvAddressManager = findViewById(R.id.tvAddressManager)
        banner_container = findViewById(R.id.banner_container)
        tvVerifySubHeading = findViewById(R.id.tvVerifySubHeading)
        phone_contenrer = findViewById(R.id.phone_contenrer)

        setSupportActionBar(toolbar)


        toolbar_tital.text = "حسابي"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015",this.window)
    }
}