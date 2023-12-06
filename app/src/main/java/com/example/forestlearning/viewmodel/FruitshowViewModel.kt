package com.example.forestlearning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.FruitShowData
import com.example.forestlearning.repository.FruitshowRepository
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FruitshowViewModel : ViewModel() {
    private val repository = FruitshowRepository()
    val fruitData: LiveData<List<FruitShowData>> = repository.fetchFruitData()
}
