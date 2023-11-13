package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding

data class StudytimeAdapter(val subject : Subjects)
    : RecyclerView.Adapter<StudytimeAdapter.Holder>() {

    var subjectlist = mutableListOf<Subjects>()

    fun setListData(data:MutableList<Subjects>){
        subjectlist = data
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentSubjecttimerBinding.inflate(LayoutInflater.from(parent.context))
        return Holder(binding)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.bind(subjectlist[position])
    }

    override fun getItemCount() = subjectlist.size

    class Holder(private val binding: FragmentSubjecttimerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private fun formatTime(time: Time): Any {
            return "${time.hour}:${time.minute}:${time.sec}"
        }

        fun bind(subject : Subjects) {
            binding.fruitView.setImageResource(
                when (subject.fruit) {
                    1 -> R.drawable.apple
                    2 -> R.drawable.banana
                    3 -> R.drawable.grape
                    else -> R.drawable.apple
                }
            )
            val time = formatTime(subject.time)
            binding.timerView.text = time.toString()
        }
    }
}

