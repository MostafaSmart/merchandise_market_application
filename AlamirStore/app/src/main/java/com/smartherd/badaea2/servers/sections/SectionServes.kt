package com.smartherd.badaea2.servers.sections

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObject
import com.smartherd.badaea2.models.Sections
import com.smartherd.badaea2.models.SubSections
import com.smartherd.badaea2.models.User

class SectionServes {

    var firestore = FirebaseFirestore.getInstance()
    fun getAllMainSections(callback: (ArrayList<Sections>?) -> Unit){
        var mainSections = ArrayList<Sections>()
        firestore.collection("sections").get().addOnSuccessListener {qq->
            qq.documents.forEach { dd ->
                var sect = dd.toObject(Sections::class.java)
                sect!!.uid = dd.id

                mainSections.add(sect)
            }
            callback(mainSections)
        }

    }


    fun getOneMainSubSections(sections: Sections,callback: (SubSections) -> Unit){
        firestore.collection("sections").document(sections.uid).collection("childer").get().addOnSuccessListener {qq->
            if(!qq.isEmpty){
                var subSections = ArrayList<Sections>()
                qq.documents.forEach {se->
                    var sect = se.toObject(Sections::class.java)
                    subSections.add(sect!!)
                }

                var ss = SubSections(sections,subSections)
                callback(ss)
            }
            else{
                callback(SubSections(sections, arrayListOf()))
            }
        }
    }


    fun getSectionByIdAndSubSection(uid:String,callback: (SubSections) -> Unit){

        var dp1 = firestore.collection("sections").document(uid)

        dp1.get().addOnSuccessListener {
            var sections = it.toObject(Sections::class.java)


            var subSections = ArrayList<Sections>()
            dp1.collection("childer").get().addOnSuccessListener {qq->
                if (!qq.isEmpty){

                    qq.documents.forEach {se->
                      var sect=  se.toObject(Sections::class.java)
                        subSections.add(sect!!)
                    }
                    var ss = SubSections(sections!!,subSections)
                    callback(ss)
                }
                else{
                    var ss = SubSections(sections!!,subSections)
                    callback(ss)
                }
            }



        }

    }
}