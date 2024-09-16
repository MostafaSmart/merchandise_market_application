package com.smartherd.badaea2.models

data class SubSections(var mainSections: Sections, var sections: ArrayList<Sections>){
    constructor():this(Sections(), arrayListOf())
}
