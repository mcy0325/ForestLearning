package com.example.forestlearning.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.CourseData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TimetableRepository {
    private val db = FirebaseDatabase.getInstance()
    private val auth = FirebaseAuth.getInstance()

    fun getCourses(): LiveData<List<CourseData>> {
        val userId = auth.currentUser?.uid ?: return MutableLiveData(emptyList())
        val liveData = MutableLiveData<List<CourseData>>()

        db.reference.child("Courses").child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val courses = snapshot.children.mapNotNull { CourseData(it.value as HashMap<String, String>) }
                liveData.value = courses
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        return liveData
    }

    fun addCourse(courseData: CourseData){
        val userId = auth.currentUser?.uid ?: return
        val courseId = db.reference.child("Courses").child(userId).push().key ?: return

        if (courseId != null) {
            db.reference.child("Courses").child(userId).child(courseId).setValue(courseData)
                .addOnSuccessListener {
                    Log.d("TimetableRepository", "addCourse: success")
                }
                .addOnFailureListener {
                    Log.d("TimetableRepository", "addCourse: failure")
                }
        }
    }

    fun removeAllCourses(userId: String) {
        val ref = FirebaseDatabase.getInstance().getReference("Courses").child(userId)
        ref.removeValue()
    }




}