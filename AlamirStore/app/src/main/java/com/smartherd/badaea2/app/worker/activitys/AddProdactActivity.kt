package com.smartherd.badaea2.app.worker.activitys

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ContextThemeWrapper
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.R
import com.smartherd.badaea2.models.Prodacts
import com.smartherd.badaea2.models.Sections
import com.smartherd.badaea2.models.SubSections
import com.smartherd.badaea2.models.Worker
import com.smartherd.badaea2.servers.prodacts.ProdactServers
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
import java.util.Calendar

class AddProdactActivity : AppCompatActivity() {
    private lateinit var edtName: EditText
    private lateinit var cbSizes: CheckBox
    private lateinit var cbImages: CheckBox
    private lateinit var cbColors: CheckBox

    private lateinit var edtDeicrip: EditText
    private lateinit var prog_add: ProgressBar
    private lateinit var toolbar_lay22: LinearLayout
    private lateinit var lilSubSection: LinearLayout
    private lateinit var listSubSection: com.google.android.material.chip.ChipGroup
    private lateinit var listMainSection: com.google.android.material.chip.ChipGroup
    private lateinit var btnAddSizes: com.google.android.material.button.MaterialButton
    private lateinit var btnAddImages: com.google.android.material.button.MaterialButton
    private lateinit var btnAddColors: com.google.android.material.button.MaterialButton
    private lateinit var btnShowSummary: com.google.android.material.button.MaterialButton
    private lateinit var btnUplaudProdact: com.google.android.material.button.MaterialButton


    private lateinit var toolbar_end: ImageView

    private lateinit var toolbar_tital: TextView
    private lateinit var toolbar_back_toggle: ImageView

    private lateinit var dialog2: Dialog

    private lateinit var cbDiscount:CheckBox
    private lateinit var btnAddDiscount:com.google.android.material.button.MaterialButton
    private lateinit var edtSizes: EditText
    private lateinit var edtPrice: EditText
    private lateinit var edtColors: EditText
    private lateinit var chOnePrice: CheckBox
    private lateinit var lilColors: LinearLayout
    private lateinit var lilSizePrice: LinearLayout
    private lateinit var toolbar_lay222: LinearLayout
    private lateinit var toolbar_lay23: LinearLayout
    private lateinit var btnSaveColor: com.google.android.material.button.MaterialButton
    private lateinit var btnFinshColors: com.google.android.material.button.MaterialButton
    private lateinit var btnSaveSizePrice: com.google.android.material.button.MaterialButton
    private lateinit var btnFinshSizePrice: com.google.android.material.button.MaterialButton

    private lateinit var edtSummaryColors:EditText
    private lateinit var edtSummarySize:EditText


    private lateinit var  lilSizeDiscount: LinearLayout
    private lateinit var edtSizesDiscount: EditText
    private lateinit var edtPriceDiscount: EditText
    private lateinit var btnSaveSizeDiscount: com.google.android.material.button.MaterialButton
    private lateinit var  btnFinshSizeDiscount: com.google.android.material.button.MaterialButton

    private lateinit var chOneDiscount:CheckBox
    private lateinit var edtSummarySizeDiscount:EditText
    private lateinit var chSizesDiscount:ChipGroup


    var sizePrice = HashMap<String,String>()
    var sizeDiscount = HashMap<String,String>()
    var colorsArray = ArrayList<String>()

    var mainSectionFlag = 0
    var subSectionFlag = 0

    var count= 0

    var discountSizeSelectArray = ArrayList<String>()

    var discountSizeSelectFlag = 0

    var loclImag = ArrayList<Uri>()

    private val getContent = registerForActivityResult(ActivityResultContracts.GetMultipleContents()){ uri->


        if(uri !=null) {

            GlobalScope.launch(Dispatchers.Main) {
                uri.forEach {tt->
                    val compressedImageFile = compress(this@AddProdactActivity,tt)

                    loclImag.add(Uri.fromFile(compressedImageFile))

                }

               cbImages.isChecked = true
            }
        }
        else{
            Toast.makeText(this,"لم يتم اختيار صور بشكل صحيح",Toast.LENGTH_SHORT)
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_add_prodact)



        extracted()



        toolbar_tital.text = "اضافة منتج"
        toolbar_back_toggle.setImageResource(R.drawable.back_icon)

        toolbar_end.setImageResource(R.drawable.baseline_notifications_none_24)
        PreferenceUsers().changeStatusBarColor("#f17015",this.window)

        toolbar_back_toggle.setOnClickListener {
            finish()
        }

        WorkerServes().getWorkerById(FirebaseAuth.getInstance().currentUser!!.uid){worker ->
            if (worker!=null){
                SectionServes().getSectionByIdAndSubSection(worker.section){subSections ->

                    instils(subSections)
                }


            }
        }



        btnAddSizes.setOnClickListener {
            lilColors.visibility = View.GONE
            btnFinshColors.visibility = View.GONE
            lilSizeDiscount.visibility = View.GONE
            lilSizePrice.visibility = View.VISIBLE
            btnFinshSizePrice.visibility = View.VISIBLE
            dialog2.show()
        }

        btnAddColors.setOnClickListener {
            lilSizePrice.visibility = View.GONE
            btnFinshSizePrice.visibility = View.GONE
            lilSizeDiscount.visibility = View.GONE
            lilColors.visibility = View.VISIBLE
            btnFinshColors.visibility = View.VISIBLE

            dialog2.show()

        }

        btnAddDiscount.setOnClickListener {

            if (sizePrice.isEmpty()){
                Toast.makeText(this,"يجب اولاً اضافة مقاس وسعر واحد على الاقل",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            lilSizePrice.visibility = View.GONE
            lilColors.visibility = View.GONE
            lilSizeDiscount.visibility = View.VISIBLE
            for (sizee in sizePrice.keys){
                var chip = createChip(sizee.toString())
                chSizesDiscount.addView(chip)
            }

            for (i in 0 until chSizesDiscount.childCount) {
                val chip = chSizesDiscount.getChildAt(i) as Chip

                chip.setOnCheckedChangeListener { buttonView, isChecked ->

                    if (isChecked) {
                        discountSizeSelectArray.add(chip.text.toString())
                        discountSizeSelectFlag = 1
                    }
                    else{
                        discountSizeSelectArray.remove(chip.text.toString())
                        if (discountSizeSelectArray.size == 0 ){
                            discountSizeSelectFlag = 0
                        }
                    }
                }
            }




            dialog2.show()
        }

        btnAddImages.setOnClickListener {
            getContent.launch("image/*")
        }

        btnSaveSizePrice.setOnClickListener {
            if(chOnePrice.isChecked){
                if (chickEmpty2()){
                    var sizes = edtSizes.text.toString().split("\n")
                    var price = edtPrice.text.toString()

                    sizes.forEach {ss->
                        sizePrice.put(ss,price)
                    }
                    showSizePriceSummary(sizePrice,edtSummarySize)
                }


            }
            else{
                if (chickEmpty2()){
                    var sizes = edtSizes.text.toString()
                    var price = edtPrice.text.toString()
                    sizePrice.put(sizes,price)
                    showSizePriceSummary(sizePrice,edtSummarySize)
                }
            }
        }

        btnSaveSizeDiscount.setOnClickListener {

            if(chickEmpty4()){

                var price = edtPriceDiscount.text.toString()
                discountSizeSelectArray.forEach { ssiez->
                    sizeDiscount.put(ssiez,price)
                }
                showSizePriceSummary(sizeDiscount,edtSizesDiscount)
            }

        }



        btnFinshSizePrice.setOnClickListener {
            if(sizePrice.isEmpty()){
                Toast.makeText(this,"يجب ادخال مقاس واحد مع سعره على الاقل",Toast.LENGTH_SHORT)
                edtSizes.setError(getString(R.string.error_field_required))
                edtPrice.setError(getString(R.string.error_field_required))

            }
            else{
                dialog2.cancel()
                Toast.makeText(this,"تمت اضافة المقاسات بنجاح",Toast.LENGTH_SHORT).show()
                cbSizes.isChecked = true
            }
        }


        btnSaveColor.setOnClickListener {
            if (edtColors.text.isEmpty()){
                edtColors.setError(getString(R.string.error_field_required))
                return@setOnClickListener
            }

            var colors = edtColors.text.toString().split("\n")
            colors.forEach { cc->
                colorsArray.add(cc)
            }
            showSizeColorsSummary(colorsArray,edtSummaryColors)

        }

        btnFinshSizeDiscount.setOnClickListener {
            if(sizeDiscount.isEmpty()){
                Toast.makeText(this,"ملاحظة : لم يتم اضافة اي تخفيض",Toast.LENGTH_SHORT)
            }
            else{
                cbDiscount.isChecked = true
            }
            dialog2.cancel()
        }

        btnFinshColors.setOnClickListener {
            if(colorsArray.isEmpty()){
                Toast.makeText(this,"ملاحظة : لم يتم اضافة اي اللوان",Toast.LENGTH_SHORT)
            }
            else{
                cbColors.isChecked = true
            }
            dialog2.cancel()
        }
    }



    private fun showSizePriceSummary(sizePr:HashMap<String,String>,editText: EditText){
        editText.text.clear()
        sizePr.keys.forEach { ss->
            editText.setText(editText.text.toString()+"\n"+ ss +" = " + sizePr.get(ss).toString())
        }
    }

    private fun showSizeColorsSummary(colo:ArrayList<String>,editText: EditText){
        editText.text.clear()
        colo.forEach { ss->
            editText.setText(editText.text.toString()+"\n"+ss)
        }
    }



    private fun instils(subSections: SubSections){



        val chip1 = createChip(subSections.mainSections.name)
        chip1.isCheckable = true
        chip1.isEnabled = false
        listMainSection.addView(chip1)


        mainSectionFlag = 1


        var selectdSubMap = HashMap<String,String>()


        if(subSections.sections.size>0){

            for (section in subSections.sections){
                val chip = createChip(section.name)
                listSubSection.addView(chip)
            }

            for (i in 0 until listSubSection.childCount) {
                val chip = listSubSection.getChildAt(i) as Chip

                chip.setOnCheckedChangeListener { buttonView, isChecked ->

                    if (isChecked){
                        selectdSubMap.put(chip.text.toString(),subSections.sections!![i].name)
                        subSectionFlag = 1

                    }
                    else{
                        selectdSubMap.remove(chip.text.toString())
                        if (selectdSubMap.count() == 0 )
                            subSectionFlag = 0
                    }
                }
            }
        }
        else{
            subSectionFlag = 2
        }



        btnUplaudProdact.setOnClickListener {
            uloudProdact(subSections.mainSections.uid,selectdSubMap)
        }


    }

    private fun uloudProdact(uid: String, selectdSubMap: java.util.HashMap<String, String>) {
        if(chickEmpty1()){

            Toast.makeText(this,"يجرى الانتظار..",Toast.LENGTH_SHORT).show()
            prog_add.visibility = View.VISIBLE

            var subSec = ArrayList<String>()
            selectdSubMap.keys.forEach {
                subSec.add(it)
            }
            var userUid = FirebaseAuth.getInstance().currentUser!!.uid
            var name = edtName.text.toString()
            var descrip = edtDeicrip.text.toString()

            var prodact = Prodacts("",userUid,uid,subSec,name,descrip, arrayListOf(),sizePrice,sizeDiscount,colorsArray,"100", Calendar.getInstance().time)

            ProdactServers().upludeProdact(prodact,loclImag){resp->
                if(resp =="done"){

                    prog_add.visibility = View.GONE
                    Toast.makeText(this,"تم رفع المنتج بنجاح ..",Toast.LENGTH_SHORT).show()
                    finish()

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

    private fun chickEmpty2():Boolean{
        return when{

            edtPrice.text.isEmpty()->{
                edtPrice.setError(getString(R.string.error_field_required))
                Toast.makeText(this,"يجب ادخال سعر واحد على الاقل",Toast.LENGTH_SHORT).show()
                false
            }
            edtSizes.text.isEmpty()->{
                edtSizes.setError(getString(R.string.error_field_required))
                Toast.makeText(this,"يجب ادخال مقاس واحد على الاقل",Toast.LENGTH_SHORT).show()
                false
            }


            else ->true
        }

        }

    private fun chickEmpty4():Boolean{
        return when{

            edtPriceDiscount.text.isEmpty()->{
                edtPriceDiscount.setError(getString(R.string.error_field_required))
                Toast.makeText(this,"يجب ادخال سعر تخفيض واحد على الاقل",Toast.LENGTH_SHORT).show()
                false
            }
            discountSizeSelectFlag == 0->{
               Snackbar.make(edtPriceDiscount,"يجب اختيار مقاس واحد للتخفيض على الاقل",Snackbar.LENGTH_SHORT).show()

                false
            }


            else ->true
        }

    }

    private fun chickEmpty1():Boolean{
        return when{

            edtName.text.isEmpty()->{
                edtName.setError(getString(R.string.error_field_required))
                false
            }

            edtDeicrip.text.isEmpty()->{
                edtDeicrip.setError(getString(R.string.error_field_required))
                false
            }

            loclImag.isEmpty()->{
                Toast.makeText(this,"يجب اولاً اختيار صور للمنتج",Toast.LENGTH_SHORT).show()
                false
            }

            sizePrice.isEmpty()->{
                Toast.makeText(this,"يجب اولاً ادخال مقاس واحد على الاقل ",Toast.LENGTH_SHORT).show()
                false
            }

            subSectionFlag == 0 ->{
                Toast.makeText(this,"يجب اختيار قسم فرعي واحد على الاقل ",Toast.LENGTH_SHORT).show()
                false
            }



            else -> {
                if(colorsArray.isEmpty()){
                    colorsArray.add("لون")
                }

                true
            }
        }
    }

    private fun extracted() {

        edtName = findViewById(R.id.edtName)
        cbSizes = findViewById(R.id.cbSizes)
        cbImages = findViewById(R.id.cbImages)
        cbColors = findViewById(R.id.cbColors)
        prog_add = findViewById(R.id.prog_add)
        edtDeicrip = findViewById(R.id.edtDeicrip)

        btnAddSizes = findViewById(R.id.btnAddSizes)
        btnAddImages = findViewById(R.id.btnAddImages)
        btnAddColors = findViewById(R.id.btnAddColors)
        toolbar_lay22 = findViewById(R.id.toolbar_lay22)
        lilSubSection = findViewById(R.id.lilSubSection)
        listSubSection = findViewById(R.id.listSubSection)
        btnShowSummary = findViewById(R.id.btnShowSummary)
        listMainSection = findViewById(R.id.listMainSection)
        btnUplaudProdact = findViewById(R.id.btnUplaudProdact)


        btnAddDiscount = findViewById(R.id.btnAddDiscount)
        cbDiscount = findViewById(R.id.cbDiscount)
        toolbar_end = findViewById(R.id.toolbar_end)
        toolbar_tital = findViewById(R.id.toolbar_tital)
        toolbar_back_toggle = findViewById(R.id.toolbar_back_toggle)


        dialog2 =  Dialog(this)
        dialog2.setContentView(R.layout.dialog_add_prodacts)





        dialog2.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        dialog2.window?.attributes?.windowAnimations = R.style.DialogAnimation

        dialog2.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog2.window?.setGravity(Gravity.BOTTOM)


        edtSizes = dialog2.findViewById(R.id.edtSizes)
        edtPrice = dialog2.findViewById(R.id.edtPrice)
        lilColors = dialog2.findViewById(R.id.lilColors)
        edtColors = dialog2.findViewById(R.id.edtColors)
        chOnePrice = dialog2.findViewById(R.id.chOnePrice)
        lilSizePrice = dialog2.findViewById(R.id.lilSizePrice)
        btnSaveColor = dialog2.findViewById(R.id.btnSaveColor)
        toolbar_lay222 = dialog2.findViewById(R.id.toolbar_lay22)
        toolbar_lay23 = dialog2.findViewById(R.id.toolbar_lay23)
        btnFinshColors = dialog2.findViewById(R.id.btnFinshColors)
        btnSaveSizePrice = dialog2.findViewById(R.id.btnSaveSizePrice)
        btnFinshSizePrice = dialog2.findViewById(R.id.btnFinshSizePrice)
        chSizesDiscount = dialog2.findViewById(R.id.chSizesDiscount)

        edtSummarySize = dialog2.findViewById(R.id.edtSummarySize)
        edtSummaryColors = dialog2.findViewById(R.id.edtSummaryColors)


        btnFinshSizeDiscount = dialog2.findViewById(R.id.btnFinshSizeDiscount)
        btnSaveSizeDiscount = dialog2.findViewById(R.id.btnSaveSizeDiscount)
        edtPriceDiscount =  dialog2.findViewById(R.id.edtPriceDiscount)
        edtSizesDiscount = dialog2.findViewById(R.id.edtSizesDiscount)
        lilSizeDiscount = dialog2.findViewById(R.id.lilSizeDiscount)

        chOneDiscount = dialog2.findViewById(R.id.chOneDiscount)
        edtSummarySizeDiscount = dialog2.findViewById(R.id.edtSummarySizeDiscount)

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

}