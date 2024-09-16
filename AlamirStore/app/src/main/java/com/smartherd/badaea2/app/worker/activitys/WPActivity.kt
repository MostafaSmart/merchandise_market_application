package com.smartherd.badaea2.app.worker.activitys

import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RatingBar
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.tabs.TabLayoutMediator
import com.smartherd.badaea2.R
import com.smartherd.badaea2.adapters.ViewPagerAdapter
import com.smartherd.badaea2.app.worker.fragments.PW_CommentsFragment
import com.smartherd.badaea2.app.worker.fragments.P_WorksFragment
import com.smartherd.badaea2.servers.worker.WorkerServes
import com.smartherd.badaea2.utils.preferences.PreferenceUsers
import com.squareup.picasso.Picasso

class WPActivity : AppCompatActivity() {
    private lateinit var rit: LinearLayout
    private lateinit var main: RelativeLayout
    private lateinit var toolbar_end: ImageView
    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView
    private lateinit var ratingBar: RatingBar
    private lateinit var txtFolowrs: TextView
    private lateinit var txtUsername: TextView
    private lateinit var txtWorkType: TextView
    private lateinit var txtDescripe: TextView
    private lateinit var per_info: LinearLayout
    private lateinit var viewPager: androidx.viewpager2.widget.ViewPager2
    private lateinit var tap_layout: com.google.android.material.tabs.TabLayout
    private lateinit var profile_image: de.hdodenhof.circleimageview.CircleImageView
    private lateinit var card_info: com.google.android.material.card.MaterialCardView
    private lateinit var btn_folow: com.google.android.material.button.MaterialButton
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_wp)


        extracted()



        var workerUid = intent.getStringExtra("workerUid")


        WorkerServes().getWorkerUserById(workerUid!!){workerUser ->

            Picasso.get().load(workerUser.worker.imgProfile).into(profile_image)
            txtUsername.text = workerUser.user.fName + " " +workerUser.user.lName
            txtDescripe.text = workerUser.worker.workeDesc

            var ratSum = 0f
            for (ratt in workerUser.ratingList){
                ratSum += ratt.rating.toFloat()

            }
            var rateingTotal = ratSum/workerUser.ratingList.size

            ratingBar.rating = rateingTotal

            txtFolowrs.text = workerUser.ratingList.size.toString()
        }


        var param1 = workerUid
        var param2 = workerUid

        val fragments = listOf(P_WorksFragment.newInstance(param1,param2),PW_CommentsFragment.newInstance(param1,param2))
        val adapter = ViewPagerAdapter(fragments, lifecycle,supportFragmentManager)
        viewPager.adapter = adapter


        TabLayoutMediator(tap_layout,viewPager){tap,pos->
            val tabView = layoutInflater.inflate(R.layout.item_tap, tap_layout, false)
            val tabIcon: ImageView = tabView.findViewById(R.id.tabIcon)
            val tabText: TextView = tabView.findViewById(R.id.tabText)

            when (pos) {
                0 -> {
                    tabIcon.setImageResource(R.drawable.about_icon)
                    tabText.text = "الاعمال"
                }

                1 -> {
                    tabIcon.setImageResource(R.drawable.about_icon)
                    tabText.text = "الاراء"
                }
                else -> {
                    tabIcon.setImageResource(R.drawable.about_icon)
                    tabText.text = "Tab"
                }
            }
            tap.customView = tabView

        }.attach()










    }

    private fun extracted() {
        rit = findViewById(R.id.rit)
        main = findViewById(R.id.main)
        per_info = findViewById(R.id.per_info)
        card_info = findViewById(R.id.card_info)
        btn_folow = findViewById(R.id.btn_folow)
        ratingBar = findViewById(R.id.ratingBar)
        viewPager = findViewById(R.id.viewPager)
        txtFolowrs = findViewById(R.id.txtFolowrs)
        tap_layout = findViewById(R.id.tap_layout)

        txtUsername = findViewById(R.id.txtUsername)
        txtWorkType = findViewById(R.id.txtWorkType)
        txtDescripe = findViewById(R.id.txtDescripe)
        profile_image = findViewById(R.id.profile_image)
        toolbar = findViewById(R.id.toolbar)
        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)

        setSupportActionBar(toolbar)
        toolbar_tital.text = "حسابي"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015",this.window)

        toolbar_back_toggle.setOnClickListener {
            finish()
        }

    }
}

