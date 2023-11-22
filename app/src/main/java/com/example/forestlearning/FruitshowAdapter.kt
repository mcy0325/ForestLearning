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
    //리사이클러뷰로 출력할 데이터 받는 곳
    val fruitData : ArrayList<FruitShowData> = arrayListOf()

    // Realtime Database에서 데이터를 불러오기 위해 선언
    val db : DatabaseReference = FirebaseDatabase.getInstance().getReference("Users")

    val originalData = mutableListOf<FruitShowData>()

    init {
        db.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                originalData.clear()
                for(userSnapshot in dataSnapshot.children) {
                    val userName = userSnapshot.child("name").getValue(String::class.java) ?: ""
                    var totalFruits = 0

                    val dayFruitSnapshot = userSnapshot.child("Dayfruit")
                    for(dateSnapshot in dayFruitSnapshot.children) {
                        for(fruitSnapshot in dateSnapshot.children) {
                            val fruitNum = fruitSnapshot.getValue(Int::class.java) ?: 0
                            totalFruits += fruitNum
                        }
                    }

                    val item = FruitShowData(userName, totalFruits)
                    originalData.add(item)
                }
                originalData.sortByDescending { it.fruitnum }
                fruitData.clear()
                fruitData.addAll(originalData)
                notifyDataSetChanged()
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // 데이터를 가져오는데 실패했을 때 처리하는 코드
            }
        })
    }

    fun search(searchName: String) {
        fruitData.clear()
        for (item in originalData) {
            if (item.nickname?.contains(searchName) == true) {
                fruitData.add(item)
            }
        }
        notifyDataSetChanged()
    }

    class Holder(val binding: FruitshowListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataBind(data: FruitShowData, position: Int) {
            binding.userName.text = data.nickname
            binding.userFruit.text = data.fruitnum.toString()
            binding.userRank.text = (position+1).toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FruitshowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.dataBind(fruitData[position], position)
    }

    override fun getItemCount() = fruitData.size

}