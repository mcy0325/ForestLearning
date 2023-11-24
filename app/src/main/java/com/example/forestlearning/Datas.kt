package com.example.forestlearning

//사용자 데이터 클래스
data class UserData(
    var name: String? = null,
    var email: String? = null,
    var uId: String? = null)

//강의 데이터 클래스
data class CourseData(
    var courseName: String? = null,
    var teacherName: String? = null,
    var day1: String? = null,
    var time1: String? = null,
    var time2: String? = null,
    var coursePlace1: String? = null,
    var day2: String? = null,
    var time3: String? = null,
    var time4: String? = null,
    var coursePlace2: String? = null)

// 누적 랭킹을 위한 데이터 클래스
data class FruitShowData(
    var nickname : String? = null,
    var fruitnum : Int = 0)
