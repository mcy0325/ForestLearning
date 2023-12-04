package com.example.forestlearning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.CourseData
import com.example.forestlearning.repository.TimetableRepository

class TimeTableViewModel : ViewModel(){
    // TimetableRepository 인스턴스 생성
    private val repository = TimetableRepository()
    // CourseData를 관리하는 MutableLiveData 생성
    private val _courseData = MutableLiveData<CourseData>()

    val courseData : MutableLiveData<CourseData> get() = _courseData

    // repository로부터 강의 정보를 가져옴
    var courses: LiveData<List<CourseData>> = repository.getCourses()

    // 강의 데이터를 설정하고 repository에 추가하는 함수
    fun setCourseData(courseName: String, teacherName: String, day1: String, time1: String, time2: String, coursePlace1: String, day2: String, time3: String, time4: String, coursePlace2: String){
        val courseData = CourseData(courseName, teacherName, day1, time1, time2, coursePlace1, day2, time3, time4, coursePlace2)
        _courseData.value = courseData
        repository.addCourse(courseData)
    }

    // 강의 데이터를 삭제하는 함수
    fun resetCourseData(courseName: String) {
        // repository에서 강의 삭제
        repository.deleteCourseByName(courseName)
        // LiveData 갱신
        courses = repository.getCourses()
    }
}