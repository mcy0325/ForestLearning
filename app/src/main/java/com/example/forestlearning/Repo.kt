package com.example.forestlearning

import android.os.Build
import androidx.annotation.RequiresApi
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
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class Repo {

    val database = Firebase.database
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val subjectsListRef = FirebaseDatabase.getInstance().getReference("SubjectsList")
    val totaltimeRef = FirebaseDatabase.getInstance().getReference("Totaltime")
    val dayfruitRef = FirebaseDatabase.getInstance().getReference("Dayfruit")

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    val todaydate: LocalDate = LocalDate.now()


    fun getdayfruitFromFirebase(){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        val dayfruitMap = mutableMapOf<Int, Int>()
        uid?.let { userUid ->
            databaseReference.child(userUid)
                .child("Dayfruit").addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        for (data in snapshot.children) {
                            val dayfruit = data.getValue(Int::class.java)
                            dayfruit?.let {
                                dayfruitMap.put(data.key!!.toInt(),it)
                            }
                        }
                        var viewModel = StudyTimeViewModel2()
                        viewModel.update_todaytreefruit(dayfruitMap)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
        }
    }
    fun updateSubjectsToFirebase(subjectsList: MutableList<Subjects>){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            subjectsListRef.child(userUid).child(todaydate.toString()).setValue(subjectsList)
        }
    }
    fun getSubjectsFromFirebase(
        subjectList: MutableLiveData<MutableList<Subjects>>){

        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            subjectsListRef.child(userUid)
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val subjectsList = mutableListOf<Subjects>()
                        for (data in snapshot.children) {
                            val subjects = data.getValue(Subjects::class.java)
                            subjects?.let {
                                subjectsList.add(it)
                            }
                        }
                        subjectList.postValue(subjectsList)
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
                }
        }



}
