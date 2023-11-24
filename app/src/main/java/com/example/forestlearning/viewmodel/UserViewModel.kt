package com.example.forestlearning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.repository.UserRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class UserViewModel : ViewModel() {
    // UserRepository 인스턴스 생성
    private val repository = UserRepository()

    // 사용자 이메일을 저장하는 MutableLiveData 생성
    private val _email = MutableLiveData("")
    val email: LiveData<String> get() = _email

    // 사용자 이름을 저장하는 MutableLiveData 생성
    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name

    // 사용자 UID를 저장하는 MutableLiveData 생성
    private val _uid = MutableLiveData("")
    val uid: LiveData<String> get() = _uid

    // 사용자 정보를 설정하고 Firebase Realtime Database에 저장하는 함수
    fun setUser(name: String, email: String, uid: String) {
        // 사용자 정보가 변경되었을 경우에만 Firebase Realtime Database에 저장
        if (_name.value != name || _email.value != email || _uid.value != uid) {
            _name.value = name
            _email.value = email
            _uid.value = uid
            repository.postUser(name, email, uid)
        }
    }

    // Firebase Realtime Database로부터 사용자 정보를 가져오는 함수
    fun fetchUser(uid: String) {
        // Firebase Realtime Database로부터 사용자 정보를 가져오기
        val db = FirebaseDatabase.getInstance()
        db.getReference("Users").child(uid).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                // Firebase Realtime Database에서 가져온 사용자 정보를 LiveData에 저장
                _name.value = snapshot.child("name").value.toString()
                _email.value = snapshot.child("email").value.toString()
                _uid.value = uid
                // UserRepository를 통해 Firebase Realtime Database에 사용자 이름과 이메일 저장
                repository.getName(uid, _name)
                repository.getEmail(uid, _email)
            }

            override fun onCancelled(error: DatabaseError) {
                // 데이터 가져오기 취소되었을 때의 처리
            }
        })
    }


}