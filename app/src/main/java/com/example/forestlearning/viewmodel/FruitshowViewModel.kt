package com.example.forestlearning.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.FruitShowData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FruitshowViewModel : ViewModel() {
    // Firebase Realtime Database 레퍼런스 선언
    private val db : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    // LiveData로 과일 데이터를 관리. LiveData는 데이터의 변경을 관찰할 수 있게 해주는 클래스
    private val _fruitData = MutableLiveData<List<FruitShowData>>()
    val fruitData: LiveData<List<FruitShowData>>
        get() = _fruitData

    // ViewModel이 생성될 때 과일 데이터를 가져오는 fetchFruitData() 함수를 호출
    init {
        fetchFruitData()
    }

    private fun fetchFruitData() {
        // Firebase Realtime Database에서 데이터를 불러오는 이벤트 리스너 설정
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터가 변경되었을 때 호출되는 메소드
                val temp = mutableListOf<FruitShowData>()
                for(userSnapshot in dataSnapshot.children) {
                    // 사용자 이름과 과일 개수를 가져옴
                    val userName = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    var totalFruits = 0

                    // 각 날짜별 과일 개수를 더함
                    val dayFruitSnapshot = userSnapshot.child("Dayfruit")
                    for(dateSnapshot in dayFruitSnapshot.children) {
                        for(fruitSnapshot in dateSnapshot.children) {
                            val fruitNum = fruitSnapshot.getValue(Int::class.java) ?: 0
                            totalFruits += fruitNum
                        }
                    }

                    // 가져온 데이터를 FruitShowData 객체로 만들어 temp에 추가
                    val item = FruitShowData(userName, totalFruits)
                    temp.add(item)
                }

                // 과일 개수를 기준으로 데이터를 정렬하고 _fruitData에 추가
                temp.sortByDescending { it.fruitnum }
                _fruitData.value = temp
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 가져오는데 실패했을 때 처리
            }
        })
    }
}
