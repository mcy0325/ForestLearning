package com.example.forestlearning

//사용자 데이터 클래스
data class UserData(
    var name: String? = null,
    var email: String? = null,
    var uId: String? = null
){
    constructor() : this(null, null, null)
}

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
    var coursePlace2: String? = null) {
    constructor(hashMap: HashMap<String, String>) : this(){
        courseName = hashMap["courseName"]
        teacherName = hashMap["teacherName"]
        day1 = hashMap["day1"]
        time1 = hashMap["time1"]
        time2 = hashMap["time2"]
        coursePlace1 = hashMap["coursePlace1"]
        day2 = hashMap["day2"]
        time3 = hashMap["time3"]
        time4 = hashMap["time4"]
        coursePlace2 = hashMap["coursePlace2"]

    }
}

// 주간 랭킹을 위한 데이터 클래스
data class FruitShowData(
    var nickname : String? = null,
    var fruitnum : Int = 0)
