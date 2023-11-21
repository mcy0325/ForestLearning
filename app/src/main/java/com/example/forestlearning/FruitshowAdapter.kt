package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FruitshowListBinding
import com.google.firebase.firestore.FirebaseFirestore

class FruitshowAdapter : RecyclerView.Adapter<FruitshowAdapter.Holder>() {
    //리사이클러뷰로 출력할 데이터 받는 곳
    val fruitData : ArrayList<FruitShowData> = arrayListOf()

    //firestore database에서 데이터 불러오기 위해 선언
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    //목록 띄우기

    init {
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestoreException ->
            fruitData.clear()
            //fruitData에 firestore 정보 넣기
            if (querySnapshot != null) {
                for(snapshot in querySnapshot.documents) {
                    val item = snapshot.toObject(FruitShowData::class.java)
                    if (item != null) {
                        fruitData.add(item)
                    }
                }
            }

            fruitData.sortByDescending { it.fruitnum }
        }
    }

    //검색 기능 구현 함수
    fun search(searchName : String) {

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