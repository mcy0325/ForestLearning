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
    // FruitshowRepository 인스턴스를 생성하여 repository 변수에 저장
    private val repository = FruitshowRepository()

    // repository를 통해 과일 데이터를 가져와 LiveData 형태로 저장
    // 과일 데이터가 변경될 때마다 자동으로 업데이트
    val fruitData: LiveData<List<FruitShowData>> = repository.fetchFruitData()
}
