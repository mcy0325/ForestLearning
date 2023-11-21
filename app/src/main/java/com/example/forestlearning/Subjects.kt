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


    fun treegetFruit() : Int{
        var count =0
        if (this.time.minute >= 60){
            count += 1
        }
        return count
    }
}

class Time(var hour: Int=0, var minute: Int=0, var sec: Int=0)
