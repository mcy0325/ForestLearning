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

    fun setUser(name: String, email: String, uid: String) {
        if (_name.value != name || _email.value != email || _uid.value != uid) {
            _name.value = name
            _email.value = email
            _uid.value = uid
            repository.postUser(name, email, uid)
        }
    }

    fun fetchUser(uid: String) {
        // Firebase Realtime Database로부터 사용자 정보를 가져오기
        val db = FirebaseDatabase.getInstance()
        db.getReference("Users").child(uid).addListenerForSingleValueEvent(object:
            ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                _name.value = snapshot.child("name").value.toString()
                _email.value = snapshot.child("email").value.toString()
                _uid.value = uid
                repository.getName(uid, _name)
                repository.getEmail(uid, _email)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }


}