package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FruitshowListBinding

class FruitshowAdapter(array:Array<FruitShowData>) : RecyclerView.Adapter<FruitshowAdapter.Holder>() {
    inner class Holder(val binding: FruitshowListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun dataBinding(data: FruitShowData) {
            binding.userName.text = data.nickname
            binding.userFruit.text = data.fruitnum.toString()
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FruitshowAdapter.Holder {
        val binding = FruitshowListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: FruitshowAdapter.Holder, position: Int) {
        //holder.dataBinding(array[position])
    }

    override fun getItemCount(): Int {
        //return array.size
        return 0}

}