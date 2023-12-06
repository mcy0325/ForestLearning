package com.example.forestlearning.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.forestlearning.FruitShowData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FruitshowRepository {
    private val db: DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    fun fetchFruitData(): LiveData<List<FruitShowData>> {
        val _fruitData = MutableLiveData<List<FruitShowData>>()
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val temp = mutableListOf<FruitShowData>()
                for (userSnapshot in dataSnapshot.children) {
                    val userName = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    var totalFruits = 0

                    val dayFruitSnapshot = userSnapshot.child("Dayfruit")
                    for (dateSnapshot in dayFruitSnapshot.children) {
                        for (fruitSnapshot in dateSnapshot.children) {
                            val fruitNum = fruitSnapshot.getValue(Int::class.java) ?: 0
                            totalFruits += fruitNum
                        }
                    }

                    val item = FruitShowData(userName, totalFruits)
                    temp.add(item)
                }

                temp.sortByDescending { it.fruitnum }
                _fruitData.value = temp
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })
        return _fruitData
    }
}
