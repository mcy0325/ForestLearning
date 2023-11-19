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

    private val repository = UserRepository()
    private val _email = MutableLiveData("")
    val email: LiveData<String> get() = _email

    private val _name = MutableLiveData("")
    val name: LiveData<String> get() = _name
    private val _uid = MutableLiveData("")

    val uid: LiveData<String> get() = _uid

    init {
        repository.getName(_name)
        repository.getEmail(_email)
    }

    fun setUser(name: String, email: String, uid: String) {
        if (_name.value != name || _email.value != email || _uid.value != uid) {
            _name.value = name
            _email.value = email
            _uid.value = uid
            repository.postUser(name, email, uid)
        }
    }

    fun fetchUser(uid: String) {
        // Firebase Realtime Database로부터 사용자 정보를 가져오는 로직을 여기에 구현합니다.
        val db = FirebaseDatabase.getInstance()
        db.getReference("Users").child(uid).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _name.value = snapshot.child("Name").value.toString()
                _email.value = snapshot.child("Email").value.toString()
                _uid.value = uid
            }

            override fun onCancelled(error: DatabaseError) {
                // Log the error or handle it appropriately.
            }
        })
    }


}