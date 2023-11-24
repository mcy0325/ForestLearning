package com.example.forestlearning.repository

import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    // Firebase 데이터베이스 인스턴스 초기화
    val database = Firebase.database
    // Firebase 데이터베이스의 "Users" 레퍼런스에 대한 참조 생성
    private val usersRef = database.getReference("Users")

    // 사용자 이름을 가져오는 함수
    fun getName(uid: String, name: MutableLiveData<String>) {
        // "Users"에 있는 해당 사용자의 "name" 레퍼런스 가져오기
        val nameRef = usersRef.child(uid).child("name")
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 데이터 변경 이벤트가 발생하면, 이름을 업데이트
                val newName = snapshot.value.toString()
                name.postValue(newName)
            }
            override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기가 취소되었을 때의 처리
            }
        })
    }

    // 사용자 이메일을 가져오는 함수
    fun getEmail(uid: String, email: MutableLiveData<String>) {
        // "Users"에 있는 해당 사용자의 "email" 레퍼런스 가져오기
        val emailRef = usersRef.child(uid).child("email")
        emailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // 데이터 변경 이벤트가 발생하면, 이메일을 업데이트
                val newEmail = snapshot.value.toString()
                email.postValue(newEmail)
            }
            override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기가 취소되었을 때의 처리
            }
        })
    }

    // 데이터베이스에 사용자 정보를 저장하는 함수
    fun postUser(name: String, email: String, uId: String) {
        // 사용자 데이터 생성
        val user = UserData(name, email, uId)
        // "Users"에 사용자 데이터 저장
        usersRef.child(uId).setValue(user)
    }
}