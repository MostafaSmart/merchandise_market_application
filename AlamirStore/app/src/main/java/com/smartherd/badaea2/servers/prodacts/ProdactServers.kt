package com.smartherd.badaea2.servers.prodacts

import android.net.Uri
import android.view.View
import android.widget.Toast
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.storage.FirebaseStorage
import com.smartherd.badaea2.models.Prodacts
import com.smartherd.badaea2.models.Worker
import java.util.Calendar
import java.util.UUID

class ProdactServers {

    var firestore = FirebaseFirestore.getInstance()
    var storege = FirebaseStorage.getInstance()

    fun upludeProdact(prodact: Prodacts,imageUriArray:ArrayList<Uri>,callback: (String) -> Unit){

        val newpostRef = firestore.collection("prodacts2").document()
        val urls =ArrayList<String>()

        val uploadTasks = imageUriArray.map { imageUri ->
            val ref = storege.getReference().child("photo2")
                .child(newpostRef.id)
                .child(UUID.randomUUID().toString())

            ref.putFile(imageUri)
                .continueWithTask { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let { throw it }
                    }
                    ref.downloadUrl
                }
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result.toString()
                        urls.add(downloadUri)

                        if (urls.size == imageUriArray.size) {
                            prodact.imgs = urls
                            prodact.uid = newpostRef.id
                            firestore.collection("prodacts2").document(newpostRef.id).set(prodact)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {

                                        callback("done")

                                    }
                                }
                        }
                    }
                }
        }

        Tasks.whenAllComplete(uploadTasks)


    }



    fun getProdactByWorker(workerUid:String,callback: ( ArrayList<Prodacts>) -> Unit ){

        var prodactArray = ArrayList<Prodacts>()
        firestore.collection("prodacts2").whereEqualTo("workerUid",workerUid).orderBy("date", Query.Direction.DESCENDING).get().addOnSuccessListener {qr->
            if (!qr.isEmpty){

                qr.documents.forEach { prodactDocoment->
                    var prod = prodactDocoment.toObject(Prodacts::class.java)!!
                    prodactArray.add(prod)
                }
                callback(prodactArray)
            }
            else{
                callback(prodactArray)
            }

        }

    }

}