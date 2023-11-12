package com.example.forestlearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.CourseData

class TimeTableViewModel : ViewModel(){
    private val _courseData = MutableLiveData<CourseData>()
    val courseData : MutableLiveData<CourseData> get() = _courseData

    fun setCourseData(courseName: String, teacherName: String, day1: String, time1: String, time2: String, day2: String, time3: String, time4: String){
        courseData.value = CourseData(courseName, teacherName, day1, time1, time2, day2, time3, time4)
    }
}