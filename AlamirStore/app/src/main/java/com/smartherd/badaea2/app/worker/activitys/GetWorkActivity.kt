package com.smartherd.badaea2.app.worker.activitys

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.R
import com.smartherd.badaea2.models.Adress
import com.smartherd.badaea2.models.PresnttionWork
import com.smartherd.badaea2.models.Sections
import com.smartherd.badaea2.models.User
import com.smartherd.badaea2.servers.sections.SectionServes
import com.smartherd.badaea2.servers.worker.WorkerServes
import com.smartherd.badaea2.utils.preferences.PreferenceUsers
import id.zelory.compressor.Compressor
import id.zelory.compressor.constraint.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream

class GetWorkActivity : AppCompatActivity() {


    private lateinit var chick_ok: CheckBox
    private lateinit var edtPhone: EditText
    private lateinit var edtAdress: EditText
    private lateinit var main: RelativeLayout
    private lateinit var toolbar: androidx.appcompat.widget.Toolbar

    private lateinit var llName: LinearLayout
    private lateinit var edtSection: EditText
    private lateinit var start_1: LinearLayout
    private lateinit var edtLastName: EditText
    private lateinit var edtDescraop: EditText
    private lateinit var edtStreet:EditText
    private lateinit var edtCity:EditText
    private lateinit var edtFirstName: EditText
    private lateinit var listMainSetctions: ChipGroup
    private lateinit var listSubSections:ChipGroup
    private lateinit var work_info: LinearLayout
    private lateinit var liContenr1: LinearLayout
    private lateinit var work_info2: LinearLayout
    private lateinit var personal_info: LinearLayout
    private lateinit var card1_: com.google.android.material.card.MaterialCardView
    private lateinit var btn_addImage: com.google.android.material.button.MaterialButton
    private lateinit var btn_contnyo_work: com.google.android.material.button.MaterialButton
    private lateinit var btn_claseAll:com.google.android.material.button.MaterialButton
    private lateinit var btn_back_work: com.google.android.material.button.MaterialButton

    private lateinit var btn_finsh: com.google.android.material.button.MaterialButton
    private lateinit var prog_uu2:ProgressBar
    private lateinit var btn_finshAll: com.google.android.material.button.MaterialButton


    private lateinit var toolbar_end: ImageView
    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView
    private lateinit var work_cmop:LinearLayout
    var mainSectionIndex = -1
    var mainSectionName = ""
    private lateinit var compressedImageUri: Uri
    var mainFlag = 0
    var subFlag = 0
    private lateinit var prog_uu:ProgressBar

    var count= 0
    var contArray = ArrayList<LinearLayout>()

    var loclImag = ArrayList<Uri>()
    var str = kotlin.collections.ArrayList<String>()


    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ uri->


        if(uri !=null) {

            GlobalScope.launch(Dispatchers.Main) {
                uri.forEach {tt->
                    val compressedImageFile = compress(this@GetWorkActivity,tt)
                    compressedImageUri = Uri.fromFile(compressedImageFile)
                    loclImag.add(compressedImageUri)
                    str.add((count++).toString())
                }

                btn_finshAll.visibility = View.VISIBLE

            }

        }


    }






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_get_work)

        extracted()
        setActionBar()

        loudData()

        btn_contnyo_work.setOnClickListener {
            when(count) {
                0 ->{
                    if(chick_ok.isChecked){
                        nextTransction(contArray[count],contArray[count+1])
                        count++

                    }
                }

                1->{
                    when {
                        chickPrInof()->{
                            nextTransction(contArray[count],contArray[count+1])
                            count++
                        }
                    }

                }

                2->{
                    nextTransction(contArray[count],contArray[count+1])
                    count++
                    btn_contnyo_work.visibility = View.GONE
                    btn_back_work.visibility = View.GONE
                    btn_claseAll.visibility = View.VISIBLE
                }
//                3->{
//                    if (edtDescraop.text.isEmpty()){
//                        edtDescraop.setError("مطلوب")
//                    }
//                    else{
//                        nextTransction(contArray[count],contArray[count+1])
//                        count++
//
//                    }
//                }

            }
        }


        btn_back_work.setOnClickListener {
            when(count){
                4->{
                    backTransction(contArray[count-1],contArray[count])
                    count--
                }
                3->{
                    backTransction(contArray[count-1],contArray[count])
                    count--
                }
                2->{
                    backTransction(contArray[count-1],contArray[count])
                    count--
                }
                1->{
                    backTransction(contArray[count-1],contArray[count])
                    count--
                }
            }
        }


        btn_addImage.setOnClickListener {
            getContent.launch("image/*")
        }

        btn_finshAll.setOnClickListener {
            if (loclImag.size>0){
                prog_uu.visibility = View.VISIBLE
                WorkerServes().upludImagePersnttion(loclImag){

                    if(it == "done"){

                        prog_uu.visibility = View.GONE
                        Toast.makeText(this,"تم التسجيل بنجاح",Toast.LENGTH_SHORT).show()
                        finish()
                    }

                }

            }
            else{
                Toast.makeText(this,"يجب اختيار صور اولاً",Toast.LENGTH_SHORT).show()
            }
        }
        getSection()





    }



    private fun getSection(){
        SectionServes().getAllMainSections {mainSectionsArrayList ->

            for (section in mainSectionsArrayList!!){
                val chip = createChip(section.name)
                listMainSetctions.addView(chip)
            }
            val chipe = createChip("غير ذلك")
            listMainSetctions.addView(chipe)

            for (i in 0 until listMainSetctions.childCount) {
                val chip = listMainSetctions.getChildAt(i) as Chip

                chip.setOnCheckedChangeListener { buttonView, isChecked ->

                    if(isChecked){
                        if (chip.text.toString() != "غير ذلك"){
                            mainSectionIndex = i
                            mainFlag = 1
                            mainSectionName = chip.text.toString()
                            selectSubSections(mainSectionsArrayList[i])
                        }
                        else{
                            edtSection.visibility= View.VISIBLE
                            listSubSections.removeAllViews()
                        }

                    }
                    else{
                        if (chip.text.toString() != "غير ذلك"){
                            mainFlag = 0
                            mainSectionIndex = -1
                            mainSectionName = ""
                            listSubSections.removeAllViews()
                        }
                        else{
                            edtSection.visibility= View.GONE
                            listSubSections.removeAllViews()
                        }

                    }
                }

            }

        }
    }

    private fun selectSubSections(section: Sections){
        listSubSections.removeAllViews()
//        var subArray = ArrayList<>

        if (section==null){

        }
        else{

            SectionServes().getOneMainSubSections(section){subSections ->
                var selectdSubMap = HashMap<String,String>()
                if (subSections.sections!=null){
                    for (section in subSections.sections!!){
                        val chip = createChip(section.name)
                        listSubSections.addView(chip)
                    }







                    for (i in 0 until listSubSections.childCount) {
                        val chip = listSubSections.getChildAt(i) as Chip

                        chip.setOnCheckedChangeListener { buttonView, isChecked ->

                            if (isChecked){
                                selectdSubMap.put(chip.text.toString(),subSections.sections!![i].name)
                                subFlag = 1

                            }
                            else{
                                selectdSubMap.remove(chip.text.toString())
                                if (selectdSubMap.count() == 0 )
                                    subFlag = 0
                            }
                        }
                    }
                }
                else{
                    subFlag = 1
                }

                btn_finsh.setOnClickListener {

                    if (edtDescraop.text.isNotEmpty() && mainFlag !=0 && subFlag !=0){
                        prog_uu.visibility = View.VISIBLE
                        var fName = edtFirstName.text.toString()
                        var lName = edtLastName.text.toString()
                        var city = edtCity.text.toString()
                        var street = edtStreet.text.toString()
                        var fullAddress = edtAdress.text.toString()
                        var phone = edtPhone.text.toString()
                        var mainS = section.uid
                        var tags = selectdSubMap
                        var workeDesc = edtDescraop.text.toString()


                        var uss= loudData()

                        var user = User(fName,lName,uss!!.email,phone,uss.type,uss.Token,uss.admin)
                        var adress = Adress(city,street,fullAddress)
                        var presnttionWork = PresnttionWork(FirebaseAuth.getInstance().currentUser!!.uid,mainS,tags,workeDesc, arrayListOf())


                        WorkerServes().craeteNewPersnttion(user,adress,presnttionWork){resp->
                            if(resp == "done"){
                                prog_uu.visibility = View.GONE

                                nextTransction(work_info2,work_cmop)

                            }
                        }


                    }



                }

            }


        }


    }


   private fun createChip(size: String): Chip {
       val themeWrapper = ContextThemeWrapper(this, R.style.AppThemeMaterial)
        val chip = Chip(themeWrapper)
        chip.text = size
        chip.isClickable = true
        chip.isCheckable = true
        return chip
    }



    private fun setActionBar() {
        setSupportActionBar(toolbar)

        toolbar_tital.text = "وضع البائع"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015", this.window)
    }


    fun chickPrInof():Boolean{
        return when {
            edtFirstName.text.isEmpty() -> {
                edtFirstName.setError(getString(R.string.error_field_required))
                false
            }


            edtLastName.text.isEmpty() -> {
                edtLastName.setError(getString(R.string.error_field_required))
                false
            }

            edtPhone.text.isEmpty() -> {
                edtPhone.setError(getString(R.string.error_field_required))
                false
            }

            edtAdress.text.isEmpty() -> {
                edtAdress.setError(getString(R.string.error_field_required))
                false
            }

            edtCity.text.isEmpty()->{
                edtCity.setError(getString(R.string.error_field_required))
                false
            }

            edtStreet.text.isEmpty()->{
                edtStreet.setError(getString(R.string.error_field_required))
                false
            }



            else -> true
        }
    }


    private fun loudData():User? {
      var user=  PreferenceUsers().getUser(this)
        if (user!=null){

            edtFirstName.setText(user.fName)
            edtLastName.setText(user.lName)
            if (user.phone.length>=9){
                edtPhone.setText(user.phone)
            }



            return user
        }
        return null
    }

    fun nextTransction(l1: LinearLayout,l2: LinearLayout){
//        val animation = TranslateAnimation(0f, -400f, 0f, 0f)
//        animation.duration = 500
//
//        l1.startAnimation(animation)
//
//        l1.visibility = View.GONE
//        l2.visibility = View.VISIBLE

        l1.animate().translationX(-700f)
            .setDuration(500)
            .withEndAction {

                l1.visibility = View.GONE
                l2.visibility = View.VISIBLE
                l1.animate().translationX(0f).start()
            }
            .start()

    }
    fun backTransction(l1: LinearLayout,l2: LinearLayout){
        l2.animate().translationX(700f)
            .setDuration(500)
            .withEndAction {

                l2.visibility = View.GONE
                l1.visibility = View.VISIBLE
                l2.animate().translationX(0f).start()
            }
            .start()


    }


    suspend fun compress(context: Context, uri: Uri): File {
        return withContext(Dispatchers.IO) {
            Compressor.compress(context, FileUtil.from(context, uri)) {
                default()
            }
        }
    }

    object FileUtil {
        fun from(context: Context, uri: Uri): File {
            val inputStream = context.contentResolver.openInputStream(uri)
            val tempFile = File.createTempFile("image", null, context.cacheDir)
            tempFile.deleteOnExit()
            val outputStream = FileOutputStream(tempFile)
            inputStream?.copyTo(outputStream)
            inputStream?.close()
            outputStream.close()
            return tempFile
        }
    }

    private fun extracted() {
        main = findViewById(R.id.main)
        card1_ = findViewById(R.id.card1_)
        llName = findViewById(R.id.llName)
        start_1 = findViewById(R.id.start_1)
        chick_ok = findViewById(R.id.chick_ok)
        edtPhone = findViewById(R.id.edtPhone)
        edtAdress = findViewById(R.id.edtAdress)
        work_info = findViewById(R.id.work_info)
        liContenr1 = findViewById(R.id.liContenr1)
        edtSection = findViewById(R.id.edtSection)
        work_info2 = findViewById(R.id.work_info2)

        edtLastName = findViewById(R.id.edtLastName)
        edtDescraop = findViewById(R.id.edtDescraop)
        edtFirstName = findViewById(R.id.edtFirstName)
        listMainSetctions = findViewById(R.id.listMainSetctions)
        btn_addImage = findViewById(R.id.btn_addImage)
        btn_claseAll = findViewById(R.id.btn_claseAll)
        listSubSections = findViewById(R.id.listSubSections)
        personal_info = findViewById(R.id.personal_info)
        btn_contnyo_work = findViewById(R.id.btn_contnyo_work)

        btn_back_work = findViewById(R.id.btn_back_work)

        work_cmop =findViewById(R.id.work_cmop)
        toolbar = findViewById(R.id.toolbar)
        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)

        edtCity = findViewById(R.id.edtCity)
        edtStreet = findViewById(R.id.edtStreet)

        prog_uu = findViewById(R.id.prog_uu)
        btn_finshAll = findViewById(R.id.btn_finshAll)
        prog_uu2 = findViewById(R.id.prog_uu2)

        btn_finsh = findViewById(R.id.btn_finsh)
        contArray.add(start_1)
        contArray.add(personal_info)
        contArray.add(work_info)
        contArray.add(work_info2)
        contArray.add(work_cmop)

    }
}