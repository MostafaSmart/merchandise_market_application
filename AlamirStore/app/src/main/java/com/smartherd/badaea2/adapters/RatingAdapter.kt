package com.smartherd.badaea2.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.smartherd.badaea2.R
import com.smartherd.badaea2.models.Rating
import com.smartherd.badaea2.models.RatingsUsers
import com.smartherd.badaea2.models.User

class RatingAdapter(val riteng:List<RatingsUsers>): RecyclerView.Adapter<RatingAdapter.Viewha>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Viewha {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_comment_rating,parent,false)



        return Viewha(v)
    }

    override fun getItemCount(): Int {
       return riteng.size
    }

    override fun onBindViewHolder(holder: Viewha, position: Int) {
       var rite = riteng[position].Rating
        var user = riteng[position].user



        holder.txtComment.text = rite.comment
        holder.txtUserName.text =user.fName
        holder.txtRiting.text = rite.rating

    }




    class Viewha(vim: View):RecyclerView.ViewHolder(vim) {



        val txtRiting = vim.findViewById<TextView>(R.id.txtRiting);
        val txtComment = vim.findViewById<TextView>(R.id.txtComment);
        val txtUserName = vim.findViewById<TextView>(R.id.txtUserName);

    }

}