package com.smartherd.badaea2.servers.users

import com.smartherd.badaea2.models.User

data class UserRespons(var user:User?,var massege:String){
    constructor() : this(User(), "")
}
