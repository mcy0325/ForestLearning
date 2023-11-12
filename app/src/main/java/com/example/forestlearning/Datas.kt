package com.example.forestlearning

data class CourseData(
    var courseName: String? = null,
    var teacherName: String? = null,
    var day1: String? = null,
    var time1: String? = null,
    var day2: String? = null,
    var time2: String? = null) {
    fun setData(courseName: String?, teacherName: String?, day1: String?, time1: String?, day2: String?, time2: String?){
        this.courseName = courseName
        this.teacherName = teacherName
        this.day1 = day1
        this.time1 = time1
        this.day2 = day2
        this.time2 = time2
    }
}
