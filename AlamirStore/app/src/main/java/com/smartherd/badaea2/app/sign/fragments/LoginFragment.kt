package com.smartherd.badaea2.app.sign.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.smartherd.badaea2.FragmentChangeListener
import com.smartherd.badaea2.R
import com.smartherd.badaea2.app.core.activitys.DashboardActivity
import com.smartherd.badaea2.models.User
import com.smartherd.badaea2.servers.users.UserServes
import com.smartherd.badaea2.utils.Connection
import com.smartherd.badaea2.utils.preferences.PreferenceUsers

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
const val request_code=2
class LoginFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var fragmentChangeListener: FragmentChangeListener? = null
    private lateinit var edtEmail: EditText
    private lateinit var tvForget: TextView
    private lateinit var tvAppName: TextView
    private lateinit var tvSubname: TextView
    private lateinit var llName: LinearLayout
    private lateinit var edtPassword: EditText
    private lateinit var btnSignGoogle: androidx.appcompat.widget.AppCompatButton
    private lateinit var btnSignIn: com.google.android.material.button.MaterialButton

    private lateinit var btnSignUp: com.google.android.material.button.MaterialButton

    private lateinit var prog_login:ProgressBar

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
        var view =  inflater.inflate(R.layout.fragment_login, container, false)


        extracted(view)


        btnSignIn.setOnClickListener{
            when {
                validate()->{
                    loginUser(view)
                }
            }
        }


        tvForget.setOnClickListener {
            view.animate().alpha(0f) // جعل الشفافية 0 (الاختفاء)
                .setDuration(500) // مدة الأنيميشن بالمللي ثانية
                .withEndAction {
                    fragmentChangeListener?.onFragmentChange(Sign_upFragment())

                }
        }

        btnSignGoogle.setOnClickListener {

            if(Connection().isConnected(view.context)){
                prog_login.visibility = View.VISIBLE

                val option = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestIdToken(getString(R.string.clint_id))
                    .requestEmail()
                    .build()
                val sghinClain = GoogleSignIn.getClient(requireActivity(),option)
                sghinClain.signInIntent.also {
                    startActivityForResult(it, request_code)
                }
            }else {
                Toast.makeText(requireActivity(),"يرجى التحقق من اتصال الانترنت !" ,Toast.LENGTH_SHORT).show()
            }
        }




        return  view
    }


    private fun loginUser(view:View){
        prog_login.visibility = View.VISIBLE
        UserServes().userLogin(edtEmail.text.toString(),edtPassword.text.toString(),view){user->
            if (user == null){
                Toast.makeText(view.context,"حدث خطاء اثناء عملية التسجيل",Toast.LENGTH_SHORT).show()
                return@userLogin
            }
            else{
                PreferenceUsers().setUser(user,FirebaseAuth.getInstance().currentUser!!.isEmailVerified,view.context)
                Toast.makeText(view.context,"مرحبا بك مجدداً",Toast.LENGTH_SHORT).show()
                prog_login.visibility = View.GONE
                reload(view.context)
            }

        }
    }

    private fun reload(context: Context) {
        val muintin = Intent(context, DashboardActivity::class.java)
        muintin.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        startActivity(muintin)
    }

    private fun validate(): Boolean {
        return when {
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
            else -> true
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode== request_code){
            if(data !=null){
                try {
                    val acount = GoogleSignIn.getSignedInAccountFromIntent(data).result
                    googleToFirebace(acount)

                }catch (ee:Exception){

                }

            }


        }
    }

    private fun googleToFirebace(acount: GoogleSignInAccount) {

        prog_login.visibility = View.VISIBLE

        val craid = GoogleAuthProvider.getCredential(acount.idToken,null)
        UserServes().signInWithCredential(craid){userRespons ->

            if (userRespons.user!=null){
                PreferenceUsers().setUser(userRespons.user!!,FirebaseAuth.getInstance().currentUser!!.isEmailVerified,requireActivity())
                prog_login.visibility = View.GONE
                reload(requireActivity())
            }
            else{
                if (userRespons.massege == "newUser"){
                    var email = FirebaseAuth.getInstance().currentUser!!.email

                    var user = User("Google","مستخدم",email!!,"0","c","","0")

                    UserServes().storeNewUser(user){userRespons ->
                        if (userRespons.user!=null){

                            PreferenceUsers().setUser(userRespons.user!!,FirebaseAuth.getInstance().currentUser!!.isEmailVerified,requireActivity())
                            prog_login.visibility = View.GONE
                            reload(requireActivity())
                        }
                        else{
                            prog_login.visibility = View.GONE
                            Toast.makeText(requireActivity(),"حدث خطاء حاول مره اخرى",Toast.LENGTH_SHORT).show()
                        }
                    }


                }
            }


        }

    }

    private fun extracted(view: View) {
        llName = view.findViewById(R.id.llName)
        edtEmail = view.findViewById(R.id.edtEmail)
        tvForget = view.findViewById(R.id.tvForget)
        tvAppName = view.findViewById(R.id.tvAppName)
        tvSubname = view.findViewById(R.id.tvSubname)
        btnSignIn = view.findViewById(R.id.btnSignIn)
        btnSignUp = view.findViewById(R.id.btnSignUp)
        edtPassword = view.findViewById(R.id.edtPassword)
        btnSignGoogle = view.findViewById(R.id.btnSignGoogle)

        prog_login = view.findViewById(R.id.prog_login)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}