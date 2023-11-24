package com.example.forestlearning

data class Subjects(
    val name : String = "",
    val info : String = "",
    val fruit : Int = 0,
    var time : Time = Time(0,0,0)
){
    // 객체 자체에서 time update 해주는 함수
    fun update_time(timeupdate : Time){
        this.time.hour = timeupdate.hour
        this.time.minute = timeupdate.minute
        this.time.sec = timeupdate.sec
    } }
class Time(var hour: Int=0, var minute: Int=0, var sec: Int=0)
