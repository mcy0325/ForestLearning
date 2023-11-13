package com.example.forestlearning.repository

import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.CourseData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class TimetableRepository {
    val database = Firebase.database
    val userRef = database.getReference("user")

    fun observeCourseData(courseData: MutableLiveData<ArrayList<CourseData>>) {
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val users = ArrayList<CourseData>()
                for(snap in snapshot.children){
                    val user = snap.getValue(CourseData::class.java)
                    user?.let {
                        users.add(it)
                    }
                }

                courseData.postValue(users)

            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
    fun addCourseData(data : CourseData){
        userRef.push().setValue(data)
    }
}