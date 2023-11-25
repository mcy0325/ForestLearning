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

    val subjectsListRef = FirebaseDatabase.getInstance().getReference("SubjectsList")
    private val databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    val todaydate: LocalDate = LocalDate.now()

    // Local date를 가져왔을 때 save 혹은 upload를 위한 String 변환 함수
    fun setDate(newDate: LocalDate) : String{
        val newDatestring = newDate.toString()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = LocalDate.parse(newDatestring, formatter)
        return date.toString()
    }

    // 오늘 획득한 fruit와 그 갯수를 저장하고 가져오는 함수
    // updatefruitToFirebase : Users - userUid - Dayfruit - todaydate
    // MutableMap<String, Int>를 인자로 받아 업데이트
    // gettreefruitFromFirebase : Users - userUid - Dayfruit - todaydate
    // MutableLiveData<Mutablemap<Int, Int>>와 date를 인자로 받아 해당 날짜의 fruit를 업데이트
    fun updatefruitToFirebase(updatefruit: MutableMap<String, Int>){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Dayfruit").child(todaydate.toString()).setValue(updatefruit)
        }
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
                                } } }
                        println(dayfruitMap)
                        fruitMap.postValue(dayfruitMap)
                    }

                    override fun onCancelled(error: DatabaseError) {}
                }) } }

    // 새로운 subject를 upload하고 가져오는 함수
    // updateSubjectToFirebase : SubjectList - UserUid - todaydate
    // MutableList를 인자로 받아 바로 저장
    // getSubjectFromFirebase : SubjectList - UserUid - todaydate
    // MutableLiveData<MutableList<Subjects>>을 인자로 받아 바로 업데이트
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
                            } }
                        subjectList.postValue(subjectsList)
                    }
                    override fun onCancelled(error: DatabaseError) {}
                }) } }

    // totaltime을 날짜별로 save하고 가져오는 함수
    // updatetotaltimeToFirebase : Users - userUid - Totaltime - todaydate
    // Time 객체를 입력받아 오늘 날짜에 저장
    // gettotaltimeFromFirebase : Users - userUid - Totaltime - todaydate
    // date와 MutableLiveData<Time>을 인자로 받아 해당 날짜의 totaltime을 받아와 업데이트
    fun updatetotaltimeToFirebase(time: Time){
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child("Users").child(userUid)
                .child("Totaltime").child(setDate(todaydate)).setValue(time)
        } }
    fun gettotaltimeFromFirebase(date: LocalDate, time: MutableLiveData<Time>){
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
                        } }

                    override fun onCancelled(error: DatabaseError) {}
                }) } }




}
