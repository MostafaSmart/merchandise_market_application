package com.smartherd.badaea2.app.core.activitys

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView

import com.smartherd.badaea2.R
import com.smartherd.badaea2.app.user.activitys.AccountActivity
import com.smartherd.badaea2.app.core.fragment.HomeFragment
import com.smartherd.badaea2.app.core.fragment.SearchFragment
import com.smartherd.badaea2.utils.preferences.PreferenceUsers

class DashboardActivity : AppCompatActivity() {

    private lateinit var main: View
    private lateinit var llLeftDrawer: LinearLayout
    private lateinit var drawerLayout: androidx.drawerlayout.widget.DrawerLayout




    private lateinit var containerSearch: FrameLayout




//    private lateinit var ivHome: ImageView
//    private lateinit var llHome: LinearLayout
//    private lateinit var llCart: LinearLayout
//    private lateinit var ivProfile: ImageView
//    private lateinit var ivWishList: ImageView
//    private lateinit var llProfile: LinearLayout
//    private lateinit var llWishList: LinearLayout


    private lateinit var tvFaq: TextView
    private lateinit var tvHelp: TextView
    private lateinit var tvAccount: TextView
    private lateinit var tvSetting: TextView
    private lateinit var tvAppName: TextView
    private lateinit var tvSubname: TextView
    private lateinit var llInfo: LinearLayout
    private lateinit var ivAppLogo: ImageView
    private lateinit var llName: LinearLayout
    private lateinit var rewardCount: TextView
    private lateinit var tvContactus: TextView
    private lateinit var llOrders: LinearLayout
    private lateinit var tvOrderCount: TextView
    private lateinit var llReward: LinearLayout
    private lateinit var tvVersionCode: TextView
    private lateinit var ivCloseDrawer: ImageView
    private lateinit var txtDisplayName: TextView
    private lateinit var tvWishListCount: TextView
    private lateinit var llWishlistData: LinearLayout
    private lateinit var rvCategory: androidx.recyclerview.widget.RecyclerView
    private lateinit var civProfile: de.hdodenhof.circleimageview.CircleImageView


    private lateinit var toolbar: androidx.appcompat.widget.Toolbar


    private lateinit var bottomAppBar: com.google.android.material.bottomappbar.BottomAppBar
    private lateinit var fab: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var bottom_nav: com.google.android.material.bottomnavigation.BottomNavigationView

    private lateinit var toolbar_end: ImageView
    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView


    var flag = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_dashboard)



        impemnt()


        bottom_nav.background=null








        setSupportActionBar(toolbar)
        setUpDrawerToggle()
        fragmntChing(HomeFragment())
        bottomNavIteamSelected()

        PreferenceUsers().changeStatusBarColor("#f17015",this.window)




        showUserInfo()

        tvAccount.setOnClickListener {
            var inte = Intent(this, AccountActivity::class.java)
            startActivity(inte)
        }

        toolbar_back_toggle.setOnClickListener {
            closeDr()
        }
        ivCloseDrawer.setOnClickListener {
            closeDr()
        }

    }

    private fun showUserInfo() {
        var user = PreferenceUsers().getUser(this)
        if (user!=null){
            txtDisplayName.text = user.fName

        }
        else{
            txtDisplayName.text = "زائر"
            tvAccount.visibility = View.GONE
        }
    }



    private fun bottomNavIteamSelected() {
        bottom_nav.setOnItemSelectedListener(object : NavigationBarView.OnItemSelectedListener {
            override fun onNavigationItemSelected(item: MenuItem): Boolean {

                when (item.itemId) {
                    R.id.home1 -> {
                        fragmntChing(HomeFragment())
                    }
//                    R.id.section -> {
//
//                    }
//                    R.id.your_order -> {
//                       startActivity(Intent(this@DashboardActivity,AccountActivity::class.java))
//                    }
//                    R.id.profile -> {
//                        fragmntChing(MypageFragment())
//                    }

                }


                return true
            }


        })
    }

    fun closeDr(){
        if(drawerLayout.isDrawerOpen(llLeftDrawer)){
            drawerLayout.closeDrawer(llLeftDrawer)
        }
        else{
            drawerLayout.openDrawer(llLeftDrawer)
        }
    }

    private fun setUpDrawerToggle() {
        val toggle = object : ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close) {

            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {
                super.onDrawerSlide(drawerView, slideOffset)
                main.translationX = slideOffset * drawerView.width
                (drawerLayout).bringChildToFront(drawerView)
                (drawerLayout).requestLayout()
            }
        }
        toggle.setToolbarNavigationClickListener {
            if (!SearchFragment().isVisible) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START)
                } else {
                    drawerLayout.openDrawer(GravityCompat.START)
                }
            }
        }
        toggle.isDrawerIndicatorEnabled = false
        val drawable = ResourcesCompat.getDrawable(resources, R.drawable.ic_drawer, theme)
        toggle.setHomeAsUpIndicator(drawable)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
    }


    fun impemnt(){
        main = findViewById(R.id.main)
        drawerLayout = findViewById(R.id.drawerLayout)
        llLeftDrawer = findViewById(R.id.llLeftDrawer)


        toolbar = findViewById(R.id.toolbar)



//        fab = findViewById(R.id.fab)
        bottom_nav = findViewById(R.id.bottom_nav)
        bottomAppBar = findViewById(R.id.bottomAppBar)





        containerSearch = findViewById(R.id.containerSearch)



//        llHome = findViewById(R.id.llHome)
//        ivHome = findViewById(R.id.ivHome)
//        llCart = findViewById(R.id.llCart)
//        llProfile = findViewById(R.id.llProfile)
//        ivProfile = findViewById(R.id.ivProfile)
//        llWishList = findViewById(R.id.llWishList)
//        ivWishList = findViewById(R.id.ivWishList)
//



		tvFaq = findViewById(R.id.tvFaq)
		llInfo = findViewById(R.id.llInfo)
		tvHelp = findViewById(R.id.tvHelp)
		llName = findViewById(R.id.llName)
		llOrders = findViewById(R.id.llOrders)
		llReward = findViewById(R.id.llReward)
		tvAccount = findViewById(R.id.tvAccount)
		tvSetting = findViewById(R.id.tvSetting)
		ivAppLogo = findViewById(R.id.ivAppLogo)
		tvAppName = findViewById(R.id.tvAppName)
		tvSubname = findViewById(R.id.tvSubname)
		civProfile = findViewById(R.id.civProfile)
		rvCategory = findViewById(R.id.rvCategory)
		rewardCount = findViewById(R.id.rewardCount)
		tvContactus = findViewById(R.id.tvContactus)
		tvOrderCount = findViewById(R.id.tvOrderCount)
		ivCloseDrawer = findViewById(R.id.ivCloseDrawer)
		tvVersionCode = findViewById(R.id.tvVersionCode)
		txtDisplayName = findViewById(R.id.txtDisplayName)
		llWishlistData = findViewById(R.id.llWishlistData)
		tvWishListCount = findViewById(R.id.tvWishListCount)


        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)



    }

    fun fragmntChing(fragment: Fragment){
        var ching = supportFragmentManager.beginTransaction()
        ching.replace(R.id.container,fragment)
        ching.commit()
        flag = false

    }
}