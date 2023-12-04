package com.example.forestlearning.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.CourseData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class TimetableRepository {
    // Firebase 인스턴스 초기화
    private val db = FirebaseDatabase.getInstance().reference
    private val auth = FirebaseAuth.getInstance()

    //사용자의 강의 데이터를 가져오는 함수
    fun getCourses(): LiveData<List<CourseData>> {
        //사용자의 UID를 가져옴
        val userId = auth.currentUser?.uid ?: return MutableLiveData(emptyList())
        val liveData = MutableLiveData<List<CourseData>>()

        //Firebase에서 강의 데이터 가져오기
        getCoursesReference(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // snapshot에서 강의 데이터를 가져와서 LiveData의 value로 설정
                val courses = snapshot.children.mapNotNull {
                    it.getValue(CourseData::class.java)
                }
                liveData.value = courses
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터를 가져오는 것이 취소되었을 때의 처리
            }
        })
        
        return liveData
    }

    // 강의를 추가하는 함수
    fun addCourse(courseData: CourseData){
        // 현재 사용자의 UID를 가져오기
        val userId = auth.currentUser?.uid ?: return
        // 새로운 강의 ID를 생성
        val courseId = getCoursesReference(userId).push().key ?: return
        // Firebase에 새로운 강의 데이터를 추가
        getCoursesReference(userId).child(courseId).setValue(courseData)
            .addOnSuccessListener {
                // 데이터 추가에 성공
            }
            .addOnFailureListener {
                // 데이터 추가에 실패
            }
    }

    fun deleteCourseByName(courseName: String) {
        val userId = auth.currentUser?.uid ?: return
        getCoursesReference(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach { dataSnapshot ->
                    val course = dataSnapshot.getValue(CourseData::class.java)
                    if (course?.courseName == courseName) {
                        dataSnapshot.ref.removeValue() // 해당 강의를 삭제
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터를 가져오는 것이 취소되었을 때의 처리
            }
        })
    }

    // 강의 데이터를 저장하고 있는 Firebase의 경로를 가져오는 함수
    private fun getCoursesReference(userId: String) = db.child("Courses").child(userId)
}