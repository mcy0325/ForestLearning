package com.example.forestlearning

import android.media.MediaCommunicationManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding

data class StudytimeAdapter(private var subjectList: MutableList<Subjects>)
    : RecyclerView.Adapter<StudytimeAdapter.Holder>() {


    fun updateData(newData: MutableList<Subjects>) {
        subjectList = newData
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentSubjecttimerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = subjectList[position]
        holder.bind(data)
    }

    override fun getItemCount(): Int = subjectList.size


    class Holder(private val binding: FragmentSubjecttimerBinding)
        : RecyclerView.ViewHolder(binding.root) {
        private fun formatTime(time: Time): String {
            return "${time.hour}:${time.minute}:${time.sec}"
        }
        fun bind(subject: Subjects) {
            binding.fruitView.setImageResource(
                when (subject.fruit) {
                    1 -> R.drawable.apple
                    2 -> R.drawable.banana
                    3 -> R.drawable.grape
                    else -> R.drawable.apple
                }
            )
            /**val time = formatTime(subject.time)
            binding.timerView.text = time**/
        }
    }
}

