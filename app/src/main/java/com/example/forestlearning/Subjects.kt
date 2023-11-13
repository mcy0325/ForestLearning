package com.example.forestlearning

data class Subjects(
    val name : String = "",
    val info : String = "",
    val fruit : Int = 0,
    val time : Time = Time(0,0,0)
)

class Time(var hour: Long, var minute: Long, var sec: Long)
