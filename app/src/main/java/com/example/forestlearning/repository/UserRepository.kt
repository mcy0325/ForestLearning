package com.example.forestlearning.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    val database = Firebase.database
    val usersRef = database.getReference("Users")


    fun getName(uid: String, name: MutableLiveData<String>) {
        val nameRef = usersRef.child(uid).child("name")
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newName = snapshot.value.toString()
                name.postValue(newName)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getEmail(uid: String, email: MutableLiveData<String>) {
        val emailRef = usersRef.child(uid).child("email")
        emailRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val newEmail = snapshot.value.toString()
                email.postValue(newEmail)
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun postUser(name: String, email: String, uId: String) {
        val user = UserData(name, email, uId)
        usersRef.child(uId).setValue(user)
    }
}