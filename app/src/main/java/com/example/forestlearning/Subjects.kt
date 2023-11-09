package com.example.forestlearning



data class Subjects(val name: String, var fruit: Int, val info: String, val time: Time) {

}

class Time(var hour: Long, var minute: Long, var sec: Long)
