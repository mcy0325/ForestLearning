package com.example.forestlearning.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.CourseData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class TimetableRepository {
    val db = FirebaseFirestore.getInstance()

    private val _courses = MutableLiveData<List<CourseData>>()
    val courses: LiveData<List<CourseData>> get() = _courses

    //getCourseData() 함수를 호출하면, 현재 로그인된 사용자의 UID를 가져와서
    //그 UID에 해당하는 사용자의 시간표 데이터를 가져온다.
    fun getCourseData() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        val courseList = mutableListOf<CourseData>()
        uid?.let { userUid ->
            db.collection("timetable").document(userUid).collection("courses")
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        val course = document.toObject(CourseData::class.java)
                        courseList.add(course)
                    }
                    _courses.value = courseList
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }
    }

    //addCourseData() 함수를 호출하면, 현재 로그인된 사용자의 UID를 가져와서
    //그 UID에 해당하는 사용자의 시간표 데이터를 추가한다.
    fun addCourseData(course: CourseData) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        uid?.let { userUid ->
            db.collection("timetable").document(userUid).collection("courses")
                .add(course)
                .addOnSuccessListener { result ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${result.id}")
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }
    }

    //deleteCourseData() 함수를 호출하면, 현재 로그인된 사용자의 UID를 가져와서
    //그 UID에 해당하는 사용자의 시간표 데이터를 모두 삭제한다.
    fun deleteCourseData(courseName: String) {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        uid?.let { userUid ->
            db.collection("timetable").document(userUid).collection("courses")
                .whereEqualTo("courseName", courseName)
                .get()
                .addOnSuccessListener { result ->
                    for (document in result) {
                        document.reference.delete()
                    }
                }
                .addOnFailureListener { exception ->
                    Log.d("TAG", "Error getting documents: ", exception)
                }
        }
    }



}