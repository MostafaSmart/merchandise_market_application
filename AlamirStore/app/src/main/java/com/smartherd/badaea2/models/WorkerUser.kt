package com.smartherd.badaea2.models

data class WorkerUser(var worker:Worker,var user: User,var ratingList: ArrayList<Rating>){
    constructor():this(Worker(), User(), arrayListOf())
}
