package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FruitshowListBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class FruitshowAdapter : RecyclerView.Adapter<FruitshowAdapter.Holder>() {
    //출력할 과일 데이터 저장할 리스트
    val fruitData : ArrayList<FruitShowData> = arrayListOf()

    //Firebase Realtime Database 레퍼런스 선언
    val db : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    //원본 과일 데이터 저장할 리스트
    val originalData = mutableListOf<FruitShowData>()

    init {
        // Firebase Realtime Database에서 데이터를 불러오는 이벤트 리스너 설정
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // 데이터가 변경되었을 때 호출되는 메소드
                originalData.clear()
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

                    // 가져온 데이터를 FruitShowData 객체로 만들어 originalData에 추가
                    val item = FruitShowData(userName, totalFruits)
                    originalData.add(item)
                }
                // 과일 개수를 기준으로 데이터를 정렬하고 fruitData에 추가
                originalData.sortByDescending { it.fruitnum }
                fruitData.clear()
                fruitData.addAll(originalData)
                notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 가져오는데 실패했을 때 처리
            }
        })
    }

    // 검색 기능 함수
    fun search(searchName: String) {
        // 검색어가 포함된 데이터만 fruitData에 추가
        fruitData.clear()
        fruitData.addAll(originalData.filter { it.nickname?.contains(searchName) == true })
        notifyDataSetChanged()
    }

    // ViewHolder 클래스
    class Holder(val binding: FruitshowListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataBind(data: FruitShowData, position: Int) {
            binding.apply {
                userName.text = data.nickname
                userFruit.text = data.fruitnum.toString()
                userRank.text = (position+1).toString()
            }
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        // ViewHolder를 생성하는 메소드
        val binding = FruitshowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        // ViewHolder에 데이터를 바인딩하는 메소드
        holder.dataBind(fruitData[position], position)
    }

    override fun getItemCount() = fruitData.size
    // 아이템 개수를 반환하는 메소드
}