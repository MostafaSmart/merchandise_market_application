package com.smartherd.badaea2.app.worker.activitys

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.GridView
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.R
import com.smartherd.badaea2.adapters.GraidAdapter
import com.smartherd.badaea2.servers.prodacts.ProdactServers
import com.smartherd.badaea2.utils.preferences.PreferenceUsers

class ProdactsManegarActivity : AppCompatActivity() {

    private lateinit var toolbar_lay: View
    private lateinit var gridProdact: GridView

    private lateinit var close_serch: ImageView
    private lateinit var search_contenrer: LinearLayout
    private lateinit var liner_filter: com.google.android.material.textfield.TextInputLayout
    private lateinit var edtSerch: com.google.android.material.textfield.MaterialAutoCompleteTextView
    private lateinit var floatingAddProdacts: com.google.android.material.floatingactionbutton.FloatingActionButton
    private lateinit var toolbar_end: ImageView

    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_prodacts_manegar)




        extracted()




        floatingAddProdacts.setOnClickListener {
            var intint = Intent(this,AddProdactActivity::class.java)
            startActivity(intint)
        }


        ProdactServers().getProdactByWorker(FirebaseAuth.getInstance().currentUser!!.uid){prodacts ->

            gridProdact.adapter = GraidAdapter(prodacts,prodacts.size,this)


        }


    }

    private fun extracted() {
        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)
        edtSerch = findViewById(R.id.edtSerch)
        toolbar_lay = findViewById(R.id.toolbar_lay)
        close_serch = findViewById(R.id.close_serch)
        gridProdact = findViewById(R.id.gridProdact)
        liner_filter = findViewById(R.id.liner_filter)
        search_contenrer = findViewById(R.id.search_contenrer)
        floatingAddProdacts = findViewById(R.id.floatingAddProdacts)

        toolbar_tital.text = "منتجاتي"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015",this.window)

        toolbar_back_toggle.setOnClickListener {
            finish()
        }
    }
}