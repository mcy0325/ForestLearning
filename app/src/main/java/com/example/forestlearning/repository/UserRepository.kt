package com.example.forestlearning.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class UserRepository {
    val database = Firebase.database
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    //realtime에서 사용자 데이터를 가져올 변수들 email, password, name
    val nameRef = database.getReference("Name")
    val emailRef = database.getReference("Email")
    val passwordRef = database.getReference("Password")

    //앱 처음 실행시 realtime에서 Name 가져와 UserViewModel로 넘겨 주기
    fun getRealTimeName(name: MutableLiveData<String>) {
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    name.postValue(snapshot.value.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //앱 처음 실행시 realtime에서 Email 가져와 UserViewModel로 넘겨 주기
    fun getRealTimeEmail(email: MutableLiveData<String>) {
        emailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.value != null) {
                    email.postValue(snapshot.value.toString())
                }
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    //로그인 시 realtime 으로 개인 정보 전달
    fun postPrivacy(name: String, email: String, password: String) {
        nameRef.setValue(name)
        emailRef.setValue(email)
        passwordRef.setValue(password)
    }

}