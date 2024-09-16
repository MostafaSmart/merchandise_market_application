package com.smartherd.badaea2.models

class RatingsUsers(var Rating:Rating,var user:User) {

    constructor():this(Rating(), User())

}