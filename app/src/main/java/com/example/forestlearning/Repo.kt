package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class Repo {
    fun getData() : LiveData<MutableList<Subjects>> {

        val mutableData = MutableLiveData<MutableList<Subjects>>()
        val database = Firebase.database
        val myRef = database.getReference("Subjects")

        myRef.addValueEventListener((object : ValueEventListener {
            val listData : MutableList<Subjects> = mutableListOf<Subjects>()

            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    listData.clear() // 실시간 데이터 업데이트시 리사이클뷰 데이터 중복방지

                    for (userSnapshot in snapshot.children){
                        val getData = userSnapshot.getValue((Subjects::class.java))
                        listData.add(getData!!)

                        mutableData.value = listData
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {


            }
        }))
        return mutableData
    }
}