package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
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

    fun getSubjectsFromFirebase(): LiveData<MutableList<Subjects>>{
        val subjectsLiveData = MutableLiveData<MutableList<Subjects>>()

        databaseReference.child("SubjectsList").addListenerForSingleValueEvent(object  : ValueEventListener{

            override fun onDataChange(snapshot: DataSnapshot) {
                val subjectsList : MutableList<Subjects> = mutableListOf()
                if (snapshot.exists()) {
                    subjectsList.clear()
                    for (subjectSnapshot in snapshot.children) {
                        val getData = subjectSnapshot.getValue(Subjects::class.java)
                        getData?.let { subjectsList.add(it) }

                        }
                }
                subjectsLiveData.postValue(subjectsList)
            }
            override fun onCancelled(error: DatabaseError) {
            }
            })
        return subjectsLiveData
        }

    fun saveSubjects(subjects: MutableList<Subjects>) {
        subjectsListRef.push().setValue(subjects)
    }

}
