package com.smartherd.badaea2.app.core.activitys

import android.os.Bundle
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import com.smartherd.badaea2.R

class MainActivity : AppCompatActivity() {
    private lateinit var home_continer: FrameLayout
    private lateinit var bottomAppBar: com.google.android.material.bottomappbar.BottomAppBar
    private lateinit var fab: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var bottom_nav: com.google.android.material.bottomnavigation.BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)


        extracted()


    }

    private fun extracted() {
        fab = findViewById(R.id.fab)
        bottom_nav = findViewById(R.id.bottom_nav)
        bottomAppBar = findViewById(R.id.bottomAppBar)
        home_continer = findViewById(R.id.home_continer)
    }
}