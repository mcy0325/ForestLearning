package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FruitshowListBinding

class FruitshowAdapter : RecyclerView.Adapter<FruitshowAdapter.Holder>() {
    private var _fruitData = listOf<FruitShowData>()
    var fruitData: List<FruitShowData>
        get() = _fruitData
        set(value) {
            _fruitData = value
            filteredData = _fruitData
            notifyDataSetChanged()
        }

    private var filteredData = listOf<FruitShowData>()

    // 검색 기능 함수
    fun search(searchName: String) {
        // 검색어가 포함된 데이터만 filteredData에 추가
        filteredData = if (searchName.isEmpty()) {
            _fruitData
        } else {
            _fruitData.filter { it.nickname?.contains(searchName) == true }
        }
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
        holder.dataBind(filteredData[position], position)
    }

    override fun getItemCount() = filteredData.size
    // 아이템 개수를 반환하는 메소드
}

