package com.smartherd.badaea2.app.sign.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.smartherd.badaea2.FragmentChangeListener
import com.smartherd.badaea2.R
import com.smartherd.badaea2.app.core.activitys.DashboardActivity
import com.smartherd.badaea2.models.User
import com.smartherd.badaea2.servers.users.UserServes
import com.smartherd.badaea2.utils.preferences.PreferencesHelper

/**
 * A simple [Fragment] subclass.
 * Use the [Sign_upFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
class Sign_upFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var edtEmail: EditText
    private lateinit var tvAppName: TextView
    private lateinit var tvSubname: TextView
    private lateinit var llName: LinearLayout
    private lateinit var edtLastName: EditText
    private lateinit var edtPassword: EditText
    private lateinit var edtFirstName: EditText
    private lateinit var edtConfirmPassword: EditText
    private lateinit var btnSignUp: com.google.android.material.button.MaterialButton
    private lateinit var btnSignIn: com.google.android.material.button.MaterialButton

    private lateinit var prog_sgin: ProgressBar
    private var fragmentChangeListener: FragmentChangeListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is FragmentChangeListener) {
            fragmentChangeListener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_sign_up, container, false)


        extracted(view)




        btnSignUp.setOnClickListener {
            when {
                validate()->{
                    createNewUser(view)
                }
            }
        }

        btnSignIn.setOnClickListener {

            view.animate().alpha(0f) // جعل الشفافية 0 (الاختفاء)
                .setDuration(500) // مدة الأنيميشن بالمللي ثانية
                .withEndAction {
                    fragmentChangeListener?.onFragmentChange(LoginFragment())

                }
        }



        return view
    }

    private fun createNewUser(view: View) {
        prog_sgin.visibility = View.VISIBLE
        var user = User(
            edtFirstName.text.toString(),
            edtLastName.text.toString(),
            edtEmail.text.toString(),
            "0",
            "c",
            "",
            "0"
        )

        UserServes().crateNewUser(user,edtPassword.text.toString()){ mass->
            if(mass == "done"){

                prog_sgin.visibility = View.GONE
                cratePrefrens(user,view.context)
                reload(view.context)

            }
            else{
                prog_sgin.visibility = View.GONE
                Toast.makeText(view.context, "حدث خطاء ", Toast.LENGTH_SHORT).show()
            }

        }
    }


    fun cratePrefrens(user: User, context: Context){
        val preferencesHelper = PreferencesHelper(context)

        preferencesHelper.isLoggedIn = true
        preferencesHelper.username = user.fName

        preferencesHelper.lname = user.lName

        preferencesHelper.email = user.email

        preferencesHelper.token = user.Token
        preferencesHelper.admin = user.admin
        preferencesHelper.confe = "0"
        preferencesHelper.id = FirebaseAuth.getInstance().currentUser!!.uid

        preferencesHelper.type = user.type
        preferencesHelper.phone = user.phone




    }


    private fun reload(context: Context) {
        val muintin = Intent(context, DashboardActivity::class.java)
        muintin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(muintin)
    }
    private fun validate(): Boolean {
        return when {
            edtFirstName.text.isEmpty() -> {
                edtFirstName.setError(getString(R.string.error_field_required))
                false
            }
            edtLastName.text.isEmpty() -> {
                edtLastName.setError(getString(R.string.error_field_required))
                false
            }
            edtEmail.text.isEmpty() -> {
                edtEmail.setError(getString(R.string.error_field_required))
                false
            }

            !Patterns.EMAIL_ADDRESS.matcher(edtEmail.text.toString()).matches() -> {
                edtEmail.setError(getString(R.string.error_enter_valid_email))
                false
            }
            edtPassword.text.isEmpty() -> {
                edtPassword.setError(getString(R.string.error_field_required))
                false
            }
            edtConfirmPassword.text.isEmpty() -> {
                edtConfirmPassword.setError(getString(R.string.error_field_required))
                false
            }
            !edtPassword.text.toString().equals(edtConfirmPassword.text.toString(), false) -> {
                edtConfirmPassword.setError(getString(R.string.error_password_not_matches))
                false
            }
            else -> true
        }
    }

    private fun extracted(view: View) {
        llName = view.findViewById(R.id.llName)
        edtEmail = view.findViewById(R.id.edtEmail)
        tvAppName = view.findViewById(R.id.tvAppName)
        tvSubname = view.findViewById(R.id.tvSubname)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        btnSignIn = view.findViewById(R.id.btnSignIn)
        edtLastName = view.findViewById(R.id.edtLastName)
        edtPassword = view.findViewById(R.id.edtPassword)
        edtFirstName = view.findViewById(R.id.edtFirstName)
        edtConfirmPassword = view.findViewById(R.id.edtConfirmPassword)

        prog_sgin = view.findViewById(R.id.prog_sgin)

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Sign_upFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Sign_upFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}