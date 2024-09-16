package com.smartherd.badaea2.servers.worker

import android.net.Uri
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.smartherd.badaea2.models.Adress
import com.smartherd.badaea2.models.PresnttionWork
import com.smartherd.badaea2.models.Rating
import com.smartherd.badaea2.models.RatingsUsers
import com.smartherd.badaea2.models.User
import com.smartherd.badaea2.models.Worker
import com.smartherd.badaea2.models.WorkerUser
import com.smartherd.badaea2.servers.users.UserServes
import java.util.UUID
import javax.security.auth.callback.Callback

class WorkerServes {

    var firestore = FirebaseFirestore.getInstance()
    var storage = FirebaseStorage.getInstance()

    fun craeteNewPersnttion(user: User,adress: Adress,presnttionWork: PresnttionWork,callback: (String) -> Unit){

        var dp2 = firestore.collection("PresnttionWork")
        var dp1 = firestore.collection("users").document(presnttionWork.userUid)
            dp1.set(user).addOnSuccessListener {
            dp1.collection("Adress").document().set(adress).addOnSuccessListener {

                dp2.document(presnttionWork.userUid).set(presnttionWork).addOnSuccessListener {
                    callback("done")
                }
            }
        }

    }

    fun upludImagePersnttion(imgUri:ArrayList<Uri>,callback: (String) -> Unit){

        var dp2 = firestore.collection("PresnttionWork")

        var urls = ArrayList<String>()
        val uploadTasks =  imgUri.map { uri ->

            val ref = storage.getReference().child("PresnttionWork")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child(UUID.randomUUID().toString())

            ref.putFile(uri).continueWithTask { task ->
                if (!task.isSuccessful) {
                    task.exception?.let { throw it }
                }
                ref.downloadUrl
            }.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result.toString()
                    urls.add(downloadUri)

                    if (urls.size == imgUri.size) {
                        var dp3 = dp2.document(FirebaseAuth.getInstance().currentUser!!.uid)

                        dp3.get().addOnSuccessListener {

                            var presnttionWork = it.toObject(PresnttionWork::class.java)
                            presnttionWork!!.img = urls

                            dp3.set(presnttionWork).addOnSuccessListener {

                                callback("done")

                            }


                        }


                    }
                }
            }
        }
        Tasks.whenAllComplete(uploadTasks)


    }


    fun getWorkerUserById(uid: String,callback: (WorkerUser) -> Unit){
        var ratingList =ArrayList<Rating>()

        firestore.collection("users").document(uid).get().addOnSuccessListener {us->
            var user = us.toObject(User::class.java)

            firestore.collection("workers").document(uid).get().addOnSuccessListener {wr->
                var worker = wr.toObject(Worker::class.java)

                firestore.collection("workers").document(uid).collection("rating").get().addOnSuccessListener {qr->
                    if(!qr.isEmpty){
                        qr.documents.forEach {
                            var rating = it.toObject(Rating::class.java)
                            ratingList.add(rating!!)
                        }
                        var workerUser = WorkerUser(worker!!,user!!,ratingList)
                        callback(workerUser)
                    }
                    else{
                        var workerUser = WorkerUser(worker!!,user!!,ratingList)
                        callback(workerUser)
                    }
                }



            }
        }


    }

    fun getWorkerById(uid:String,callback: (Worker?) -> Unit){
        firestore.collection("workers").document(uid).get().addOnSuccessListener {
            if(it.exists()){

                var worker = it.toObject(Worker::class.java)
                callback(worker)

            }
            else{
                callback(null)
            }
        }

    }


    fun getAllRatingByWorkerUid(uid:String,callback: (ArrayList<RatingsUsers>) -> Unit){
        var ratingList =ArrayList<RatingsUsers>()

        firestore.collection("workers").document(uid).collection("rating").get().addOnSuccessListener { rr->
            if(!rr.isEmpty){

                var count = 0
                rr.documents.forEach {
                    var rating = it.toObject(Rating::class.java)

                    firestore.collection("users").document(rating!!.userRatUid).get().addOnSuccessListener {us->
                        var userr = us.toObject(User::class.java)
                        var ratingsUsers = RatingsUsers(rating,userr!!)
                        ratingList.add(ratingsUsers)
                        count++

                        if(count == rr.size()){

                            callback(ratingList)
                        }



                    }


                }


            }else
                callback(ratingList)

        }


    }


}