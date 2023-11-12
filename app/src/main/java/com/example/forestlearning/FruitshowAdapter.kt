package com.example.forestlearning

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore
import com.example.forestlearning.databinding.FruitshowListBinding

@SuppressLint("NotifyDataSetChanged")
class FruitshowAdapter : RecyclerView.Adapter<FruitshowAdapter.FruitshowHolder>() {
    // 리사이클러뷰로 출력할 데이터를 받기 위해 ArrayList를 선언
    val userData : ArrayList<FireStoreData> = arrayListOf()

    // Firestore Database에서 데이터를 불러오기 위해 FirebaseFirestore를 선언
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    init {
        db.collection("Users").addSnapshotListener { querySnapshot, firebaseFirestroeException -> userData.clear()
            //userData 안에 cloud firestore에 담긴 모든 정보가 들어온다
            if (querySnapshot != null) {
                for (snapshot in querySnapshot.documents) {
                    val item = snapshot.toObject(FireStoreData::class.java)
                    if (item != null) {
                        userData.add(item)
                    }
                }
            }

            //내림차순으로 정렬
            userData.sortByDescending {it.fruitnum}
            notifyDataSetChanged()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitshowAdapter.FruitshowHolder {
        val binding = FruitshowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FruitshowHolder(binding)
    }

    override fun onBindViewHolder(holder: FruitshowAdapter.FruitshowHolder, position: Int) {
        holder.bind(userData[position], position)
    }

    override fun getItemCount() = userData.size

    //검색 함수 만들어야 함(이름으로 검색)

    class FruitshowHolder(private val binding : FruitshowListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user : FireStoreData, position: Int) {
            binding.userName.text = user.nickname
            binding.userFruit.text = user.fruitnum.toString()
            binding.userRank.text = (position+1).toString()
        }
    }
}