package com.smartherd.badaea2.servers.users

import android.content.Intent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import com.google.api.Context
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.smartherd.badaea2.models.User

class UserServes {

    val auth = FirebaseAuth.getInstance()
    val firestore = FirebaseFirestore.getInstance()



    fun userLogin(email:String,password: String,view: View,callback: (User?) -> Unit){

        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener { task->
            if (task.isSuccessful){

                var dp2 = firestore.collection("users").document(auth.currentUser!!.uid)
                    dp2.get().addOnSuccessListener {usDoc->
                    if (usDoc.exists()){
                        var user = usDoc.toObject(User::class.java)!!

                        FirebaseMessaging.getInstance().token.addOnSuccessListener { taskToken ->
                            FirebaseMessaging.getInstance().subscribeToTopic("sendAll").addOnSuccessListener { sub ->
                                user.Token = taskToken.toString()

                                dp2.set(user).addOnSuccessListener {

                                    callback(user)

                                }.addOnFailureListener {
                                    callback(null)
                                }

                            }.addOnFailureListener {
                                callback(null)
                            }

                        }.addOnFailureListener {
                            callback(null)
                        }
                    }
                        else{
                        callback(null)
                    }
                }.addOnFailureListener {
                        callback(null)
                    }
            }
            else{
                callback(null)
            }
        }

    }


    fun updateUser(user: User,callback: (String) -> Unit){
        if (auth.currentUser== null){
            callback("حدث خطاء !")
        }
        else{
            firestore.collection("users").document(auth.currentUser!!.uid).set(user).addOnCompleteListener {task->
                if(task.isSuccessful){
                    callback("done")
                }
                else{
                    callback("حدث خطاء !")
                }
            }
        }

    }


    fun confermUserEmail(callback: (String) -> Unit){
        if (auth.currentUser!=null){
            auth.currentUser!!.sendEmailVerification().addOnCompleteListener { task->
                if (task.isSuccessful){
                    callback("done")
                }
                else{
                    callback("حدث خطاء !")
                }
            }
        }
        else{
            callback("حدث خطاء حاول تسجيل الدخول مجددا والمحاولة")
        }
    }
    fun crateNewUser(user: User,password:String,callback: (String) -> Unit){
        auth.createUserWithEmailAndPassword(user.email,password).addOnCompleteListener { one->
            if (one.isSuccessful){

                FirebaseMessaging.getInstance().token.addOnSuccessListener { taskToken ->

                    FirebaseMessaging.getInstance().subscribeToTopic("sendAll").addOnSuccessListener{sub->
                        user.Token = taskToken.toString()
                        firestore.collection("users").document(auth.currentUser!!.uid).set(user).addOnSuccessListener {done->
                            callback("done")

                        }.addOnFailureListener {ex->
                                callback (ex.message.toString())
                        }

                    }.addOnFailureListener { ex->
                        callback (ex.message.toString())

                    }
                }.addOnFailureListener {ex->
                    callback (ex.message.toString())
                }


            }
            else{
                callback (one.exception!!.message.toString())
            }
        }



    }
    fun signInWithCredential(craid:AuthCredential,callback: (UserRespons) -> Unit){

        auth.signInWithCredential(craid).addOnCompleteListener{googlee->
            if(googlee.isSuccessful){

               var dp1= firestore.collection("users").document(auth.currentUser!!.uid)
                   dp1.get().addOnSuccessListener{log->
                    if(log.exists()){

                        var user = log.toObject(User::class.java)!!
                        FirebaseMessaging.getInstance().token.addOnSuccessListener { taskToken ->
                            FirebaseMessaging.getInstance().subscribeToTopic("sendAll").addOnSuccessListener { sub ->
                                user.Token = taskToken.toString()
                                dp1.set(user).addOnSuccessListener {
                                    callback(UserRespons(user,"Success"))
                                }

                            }
                        }

                    }
                       else{
                        callback(UserRespons(null,"newUser"))
                    }
                }

            }
            else{
                callback(UserRespons(null,"Failure + ${googlee.exception!!.message}"))
            }
        }

    }


    fun storeNewUser(user: User,callback: (UserRespons) -> Unit){

        FirebaseMessaging.getInstance().token.addOnSuccessListener { taskToken ->
            FirebaseMessaging.getInstance().subscribeToTopic("sendAll").addOnSuccessListener { sub ->
             user.Token = taskToken.toString()
                firestore.collection("users").document(auth.currentUser!!.uid).set(user).addOnSuccessListener {
                    callback(UserRespons(user,"Success"))
                }.addOnFailureListener {
                    callback(UserRespons(null,it.message.toString()))

                }
            }
        }



    }


    fun chickUserConfremdEmail():Boolean{
        if(auth.currentUser!=null){
            if(auth.currentUser!!.isEmailVerified){
                return true
            }
            else{
                return false
            }
        }
        else{
            return false
        }

    }



}