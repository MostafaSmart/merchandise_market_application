package com.smartherd.badaea2.adapters

import android.content.Context
import android.text.SpannableString
import android.text.Spanned
import android.text.style.StrikethroughSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import com.smartherd.badaea2.R
import com.smartherd.badaea2.models.Prodacts
import com.squareup.picasso.Picasso

class GraidAdapter(var prodacts:ArrayList<Prodacts>,var countd:Int,var context: Context): BaseAdapter() {
    override fun getCount(): Int {
        return countd
    }

    override fun getItem(position: Int): Any {
        TODO("Not yet implemented")
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = LayoutInflater.from(context).inflate(R.layout.item_test_prodact,parent,false)


        val data = prodacts[position]
		val prod3_img = view.findViewById<ImageView>(R.id.prod3_img);
		val prod3_name = view.findViewById<TextView>(R.id.prod3_name);
		val prod3_desc = view.findViewById<TextView>(R.id.prod3_desc);
		val prod3_price = view.findViewById<TextView>(R.id.prod3_price);
		val prod3_dcount = view.findViewById<TextView>(R.id.prod3_dcount);
		val hart_icon_show = view.findViewById<ImageView>(R.id.hart_icon_show);
		val prod_paran_d = view.findViewById<RelativeLayout>(R.id.prod_paran_d);
		val prod3_dcount_pres = view.findViewById<TextView>(R.id.prod3_dcount_pres);


        try {



        prod3_name.text = data.name

        if (!data.sizeDiscount.isEmpty()){
           var size = data.sizeDiscount.keys.firstOrNull()
            var descount = data.sizeDiscount.get(size).toString()!!
            var price = data.sizePrice.get(size).toString()!!

            val priceWithStrikeThrough = SpannableString(price + "ريال ")
            priceWithStrikeThrough.setSpan(StrikethroughSpan(), 0, priceWithStrikeThrough.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

            var total = price.toDouble() - descount.toDouble()
            var prcent =  ((total / price.toDouble()) * 100).toInt()


            prod3_dcount_pres.text = prcent.toString()+" %"
            prod3_dcount.text = descount+ " ريال"
            prod3_price.text = priceWithStrikeThrough
            prod3_price.setTextColor(android.graphics.Color.GRAY)

        }
        else{
            var size = data.sizePrice.keys.firstOrNull()
            var price = data.sizePrice.get(size).toString()
            prod3_dcount.visibility = View.GONE
            prod3_dcount_pres.visibility = View.GONE

            prod3_price.text = price
        }

        if(data.imgs.size>0){
            Picasso.get().load(data.imgs[0]).into(prod3_img)
        }


        }catch (e:Exception){
            Toast.makeText(context,"هناك خطاء في الاتصال",Toast.LENGTH_SHORT).show()
            Log.d("graidCrash",e.message.toString())
        }



        return view

    }


}