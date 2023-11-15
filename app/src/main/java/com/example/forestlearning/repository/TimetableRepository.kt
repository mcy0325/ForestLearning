package com.example.forestlearning.repository

import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.CourseData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

class TimetableRepository {
    val database = Firebase.database
    val db : FirebaseFirestore = Firebase.firestore

    //realtime에서 사용자 데이터를 가져올 변수들

    val courseNameRef = database.getReference("CourseName")
    val teacherNameRef = database.getReference("TeacherName")
    val day1Ref = database.getReference("Day1")
    val time1Ref = database.getReference("Time1")
    val time2Ref = database.getReference("Time2")
    val coursePlace1Ref = database.getReference("CoursePlace1")
    val day2Ref = database.getReference("Day2")
    val time3Ref = database.getReference("Time3")
    val time4Ref = database.getReference("Time4")
    val coursePlace2Ref = database.getReference("CoursePlace2")


}