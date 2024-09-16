package com.smartherd.badaea2

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import android.widget.Toast

class AboutActivity : AppCompatActivity() {
    private lateinit var back_abut: ImageView
    private lateinit var contact_whtaspp: ImageView
    private lateinit var contact_instagram: ImageView
    private lateinit var about_contener:LinearLayout
    private lateinit var about_tital:TextView
    private lateinit var countener_grafty:ScrollView
    private lateinit var contenr_store:ScrollView
    private lateinit var go_googleMaps:Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)


        imelmnt()

        var aboutDev = intent.getStringExtra("aboutme")
        var grafty = intent.getStringExtra("gravty")
        var store = intent.getStringExtra("storeabo")

        if(aboutDev!=null){
            about_contener.visibility = View.VISIBLE
            about_tital.text ="عن المطور"


        }
        if(grafty!=null){
            countener_grafty.visibility = View.VISIBLE
            about_tital.text= "سياسة الخصوصية"
        }
        if(store!=null){
            contenr_store.visibility = View.VISIBLE
            about_tital.text ="عن متجر الامير"
        }



        back_abut.setOnClickListener {
            finish()
        }


        contact_whtaspp.setOnClickListener {
            goWhatsapp()
        }
        contact_instagram.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/65sw_/"))
            startActivity(Intent.createChooser(intent, "اختر تطبيق للفتح"))
        }
        go_googleMaps.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://maps.app.goo.gl/GMNs1Snm6n89WRe46"))
            startActivity(Intent.createChooser(intent, "اختر تطبيق للفتح"))

        }



    }
    private fun goWhatsapp() {
        val phoneNumber = "777296572"
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/$phoneNumber"))

        // الحصول على قائمة بجميع التطبيقات المثبتة التي يمكن استخدامها لفتح النية
        val packageManager = packageManager
        val activities = packageManager.queryIntentActivities(intent, 0)
        val isIntentSafe = activities.isNotEmpty()

        if (isIntentSafe) {
            // عرض خيارات للمستخدم لتحديد التطبيق الذي يناسبه لفتح النية
            val chooser = Intent.createChooser(intent, "Open with")
            startActivity(chooser)
        } else {
            // في حالة عدم توفر تطبيقات WhatsApp على جهاز المستخدم، يتم عرض رسالة خطأ للمستخدم
            Toast.makeText(
                this,
                "WhatsApp is not installed on your device",
                Toast.LENGTH_SHORT
            ).show()
        }
    }


    private fun imelmnt() {
        back_abut = findViewById(R.id.back_abut)
        contact_whtaspp = findViewById(R.id.contact_whtaspp)
        contact_instagram = findViewById(R.id.contact_instagram)
        about_contener = findViewById(R.id.about_contener)
        about_tital = findViewById(R.id.about_tital)
        countener_grafty= findViewById(R.id.countener_grafty)
        contenr_store = findViewById(R.id.contenr_store)
        go_googleMaps = findViewById(R.id.go_googleMaps)
    }
}