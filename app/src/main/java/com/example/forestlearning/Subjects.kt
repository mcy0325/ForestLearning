package com.example.forestlearning

data class Subjects(
    val name : String = "",
    val info : String = "",
    val fruit : Int = 0,
    val time : Time = Time(0,0,0)
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

class Time(var hour: Long, var minute: Long, var sec: Long)
