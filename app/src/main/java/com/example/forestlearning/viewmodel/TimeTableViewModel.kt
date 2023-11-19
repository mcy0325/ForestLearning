package com.example.forestlearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.CourseData
import com.example.forestlearning.repository.TimetableRepository

class TimeTableViewModel : ViewModel(){
    private val repository = TimetableRepository()
    private val _courseData = MutableLiveData<CourseData>()

    //repository에서 가져온 데이터를 _courseData에 저장한다.
    val courses = repository.courses

    //repository를 사용해 데이터베이스에 값 저장
    fun addCourseData(course: CourseData) {
        repository.addCourseData(course)
    }

    //repository를 사용해 데이터베이스에서 강의 삭제 후 업데이트
    fun deleteCourseData(course: CourseData) {
        //repository.deleteCourseData(course)
    }

    //firestore에서 모든 강의를 불러와 업데이트
    fun getCourseData() {
        repository.getCourseData()
    }

    val courseData : MutableLiveData<CourseData> get() = _courseData



    fun setCourseData(courseName: String, teacherName: String, day1: String, time1: String, time2: String, coursePlace1: String, day2: String, time3: String, time4: String, coursePlace2: String){
        courseData.value = CourseData(courseName, teacherName, day1, time1, time2, coursePlace1, day2, time3, time4, coursePlace2)
    }
}