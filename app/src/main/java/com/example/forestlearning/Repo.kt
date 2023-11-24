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
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class Repo {

    val database = Firebase.database
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    val subjectsListRef = FirebaseDatabase.getInstance().getReference("SubjectsList")
    val totaltimeRef = FirebaseDatabase.getInstance().getReference("Totaltime")
    val dayfruitRef = FirebaseDatabase.getInstance().getReference("Dayfruit")

    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    val todaydate: LocalDate = LocalDate.now()

    fun updatefruitToFirebase(updatefruit: MutableMap<String, Int>){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Dayfruit").child(todaydate.toString()).setValue(updatefruit)
        }
    }
    fun setDate(newDate: LocalDate) : String{
        val newDatestring = newDate.toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(newDatestring, formatter)
        return date.toString()
    }
    fun gettreefruitFromFirebase(fruitMap: MutableLiveData<MutableMap<Int, Int>>, date: LocalDate){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Dayfruit").child(setDate(date))
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val dayfruitMap = mutableMapOf<Int, Int>()
                        for (data in snapshot.children) {
                            val dayfruit = data.getValue(Int::class.java)
                            dayfruit?.let {
                                data.key?.toIntOrNull()?.let { key ->
                                    dayfruitMap.put(key, it)
                                }
                            }
                        }
                        println(dayfruitMap)
                        fruitMap.postValue(dayfruitMap)
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
            subjectsListRef.child(userUid).child(todaydate.toString())
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
    fun gettotaltime(date: LocalDate, time: MutableLiveData<Time>){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Totaltime").child(setDate(date))
                .addValueEventListener(object : ValueEventListener {
                    override fun onDataChange(snapshot: DataSnapshot) {
                        val totaltime = snapshot.getValue(Time::class.java)
                        totaltime?.let {
                            time.postValue(it)
                        }
                    }

                    override fun onCancelled(error: DatabaseError) {
                        // Failed to read value
                    }
                })
        }
    }

    fun updatetotaltime(time: Time){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Totaltime").child(setDate(todaydate)).setValue(time)
        }
    }


}
