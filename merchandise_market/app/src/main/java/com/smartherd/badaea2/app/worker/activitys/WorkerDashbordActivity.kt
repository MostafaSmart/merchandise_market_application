package com.smartherd.badaea2.app.worker.activitys

import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.R
import com.smartherd.badaea2.servers.worker.WorkerServes
import com.smartherd.badaea2.utils.preferences.PreferenceUsers
import com.squareup.picasso.Picasso

class WorkerDashbordActivity : AppCompatActivity() {

    private lateinit var tvInfo: TextView
    private lateinit var tvSelles: TextView
    private lateinit var tvRating: TextView

    private lateinit var tvProdacts: TextView
    private lateinit var tvWishlist: TextView
    private lateinit var tvHelpCenter: TextView
    private lateinit var prog_acount: ProgressBar
    private lateinit var txtDisplayName: TextView

    private lateinit var toolbar_end: ImageView

    private lateinit var toolbar_tital: TextView
    private lateinit var tvMyPage:TextView
    private lateinit var toolbar_back_toggle: ImageView


    private lateinit var profile_image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var btnSignOut: com.google.android.material.button.MaterialButton


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_worker_dashbord)


        extracted()


        var userName = intent.getStringExtra("userName")
        var userUid = FirebaseAuth.getInstance().currentUser!!.uid


        txtDisplayName.text = userName.toString()

        WorkerServes().getWorkerById(userUid){worker ->
            Picasso.get().load(worker!!.imgProfile).into(profile_image)
        }

        tvMyPage.setOnClickListener {
            var intint = Intent(this,WPActivity::class.java)
            intint.putExtra("workerUid",userUid)
            startActivity(intint)
        }

        tvProdacts.setOnClickListener {
            var intint = Intent(this,ProdactsManegarActivity::class.java)
            startActivity(intint)
        }
    }

    private fun extracted() {
        tvInfo = findViewById(R.id.tvInfo)
        tvSelles = findViewById(R.id.tvSelles)
        tvRating = findViewById(R.id.tvRating)

        tvProdacts = findViewById(R.id.tvProdacts)
        tvWishlist = findViewById(R.id.tvWishlist)
        btnSignOut = findViewById(R.id.btnSignOut)
        prog_acount = findViewById(R.id.prog_acount)

        tvHelpCenter = findViewById(R.id.tvHelpCenter)
        profile_image = findViewById(R.id.profile_image)
        txtDisplayName = findViewById(R.id.txtDisplayName)
        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)

        tvMyPage  = findViewById(R.id.tvMyPage)


        toolbar_tital.text = "حسابي التجاري"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015",this.window)

    }
}