package com.example.forestlearning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.CourseData
import com.example.forestlearning.repository.TimetableRepository

class TimeTableViewModel : ViewModel(){
    private val repository = TimetableRepository()
    private val _courseData = MutableLiveData<CourseData>()

    val courseData : MutableLiveData<CourseData> get() = _courseData

    var courses: LiveData<List<CourseData>> = repository.getCourses()

    fun setCourseData(courseName: String, teacherName: String, day1: String, time1: String, time2: String, coursePlace1: String, day2: String, time3: String, time4: String, coursePlace2: String){
        val courseData = CourseData(courseName, teacherName, day1, time1, time2, coursePlace1, day2, time3, time4, coursePlace2)
        _courseData.value = courseData
        repository.addCourse(courseData)
    }

    fun deleteAllCourses(userId: String) {
        repository.removeAllCourses(userId)
        courses = repository.getCourses()
    }



}