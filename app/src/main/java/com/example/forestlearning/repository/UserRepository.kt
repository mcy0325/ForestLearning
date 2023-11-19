package com.example.forestlearning.repository

import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.UserData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class UserRepository {
    val database = Firebase.database

    val nameRef = database.getReference("name")
    val emailRef = database.getReference("email")
    val usersRef = database.getReference("Users")

    fun getName(name: MutableLiveData<String>) {
        nameRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                name.postValue(snapshot.value.toString())
            }
            override fun onCancelled(error: DatabaseError) {
            }
        })
    }

    fun getEmail(email: MutableLiveData<String>) {
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

    fun postUser(name: String, email: String, uId: String) {
        val user = UserData(name, email, uId)
        usersRef.child(uId).setValue(user)
    }
}