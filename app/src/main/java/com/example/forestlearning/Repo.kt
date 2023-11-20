package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import com.google.firebase.firestore.FirebaseFirestore

class Repo {

    val database = Firebase.database
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val subjectsListRef = database.getReference("SubjectsList")
    val totaltimeRef = database.getReference("Totaltime")
    val dayfruitRef = database.getReference("Dayfruit")

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    fun getSubjectsFromFirebase(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        val subjectsList = mutableListOf<Subjects>()
        uid?.let { userUid ->
            databaseReference.child(userUid)
                .child("SubjectsList").addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    for (data in snapshot.children) {
                        val subjects = data.getValue(Subjects::class.java)
                        subjects?.let {
                            subjectsList.add(it)
                        }
                    }
                    var viewModel = StudyTimeViewModel2()
                    viewModel.updateSubjects(subjectsList)
                }

                override fun onCancelled(error: DatabaseError) {
                    // Failed to read value
                }
            })
        }
        }

    fun saveSubjects(subjects: MutableList<Subjects>) {
        subjectsListRef.push().setValue(subjects)
    }

}
