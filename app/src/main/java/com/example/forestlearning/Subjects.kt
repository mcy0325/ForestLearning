package com.example.forestlearning

data class Subjects(
    val name : String = "",
    val info : String = "",
    val fruit : Int = 0,
    var time : Time = Time(0,0,0)
){
    fun update_time(timeupdate : Time){
        this.time.hour += timeupdate.hour
        this.time.minute += timeupdate.minute
        this.time.sec += timeupdate.sec
    }

    //fun treegetFruit() : Int{
        //if (this.time.hour)

    //}
}

class Time(var hour: Long=0, var minute: Long=0, var sec: Long=0)
