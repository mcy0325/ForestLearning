package com.example.forestlearning

import android.media.MediaCommunicationManager
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding



class StudytimeAdapter : ListAdapter<Subjects, StudytimeAdapter.Holder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentSubjecttimerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }


    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }


    class Holder(val binding: FragmentSubjecttimerBinding)
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
            val time = formatTime(subject.time)
            binding.timerView.text = time
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Subjects>() {
        override fun areItemsTheSame(oldItem: Subjects, newItem: Subjects): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: Subjects, newItem: Subjects): Boolean {
            return oldItem == newItem
        }
    }
}

