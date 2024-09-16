package com.smartherd.badaea2.app.worker.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.smartherd.badaea2.R
import com.smartherd.badaea2.adapters.RatingAdapter
import com.smartherd.badaea2.models.Rating
import com.smartherd.badaea2.servers.worker.WorkerServes

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [PW_CommentsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PW_CommentsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var rvComments:RecyclerView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view =  inflater.inflate(R.layout.fragment_pw_comments, container, false)



        extracted(view)

        rvComments.layoutManager = LinearLayoutManager(view.context)
        param1?.let {

            WorkerServes().getAllRatingByWorkerUid(it.toString()){ratings->

                if (!ratings.isEmpty()){
                rvComments.adapter = RatingAdapter(ratings)

                }
                else{
                    Snackbar.make(rvComments,"emtey",Snackbar.LENGTH_SHORT).show()
                }
            }

        }


        return view
    }

    private fun extracted(view: View) {
        rvComments = view.findViewById(R.id.rvComments)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment P_WorksFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PW_CommentsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

//
//import com.google.gson.Gson
//
//data class Rating(var userRatUid: String, var rating: String) {
//    constructor() : this("", "")
//}
//
//class PW_CommentsFragment : Fragment() {
//    private var rating: Rating? = null
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        arguments?.let {
//            rating = Gson().fromJson(it.getString(ARG_RATING), Rating::class.java)
//        }
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        val view = inflater.inflate(R.layout.fragment_p_works, container, false)
//
//        // استخدم المتغير rating هنا لتحديث عناصر واجهة المستخدم في الفراجمنت
//        rating?.let {
//            // مثال: إذا كان لديك TextView في تخطيط الفراجمنت لعرض التقييم
//            val ratingTextView: TextView = view.findViewById(R.id.ratingTextView)
//            ratingTextView.text = it.rating
//        }
//
//        return view
//    }
//
//    companion object {
//        private const val ARG_RATING = "rating"
//
//        @JvmStatic
//        fun newInstance(rating: Rating) =
//            P_WorksFragment().apply {
//                arguments = Bundle().apply {
//                    putString(ARG_RATING, Gson().toJson(rating))
//                }
//            }
//    }
//}

