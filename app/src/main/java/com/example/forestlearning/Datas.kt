package com.example.forestlearning

data class CourseData(
    var courseName: String? = null,
    var teacherName: String? = null,
    var day1: String? = null,
    var time1: String? = null,
    var time2: String? = null,
    var day2: String? = null,
    var time3: String? = null,
    var time4: String? = null) {
    fun setData(courseName: String?, teacherName: String?, day1: String?, time1: String?, time2: String?, day2: String?, time3: String?, time4: String?){
        this.courseName = courseName
        this.teacherName = teacherName
        this.day1 = day1
        this.time1 = time1
        this.time2 = time2
        this.day2 = day2
        this.time3 = time3
        this.time4 = time4
    }
}

// 주간 랭킹을 위한 데이터 클래스
data class FireStoreData(
    var nickname : String? = null,
    var fruitnum : Int = 0)