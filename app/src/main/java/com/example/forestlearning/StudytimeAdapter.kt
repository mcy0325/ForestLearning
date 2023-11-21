package com.example.forestlearning

import android.media.MediaCommunicationManager
import android.os.Build
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class StudytimeAdapter()
    : ListAdapter<Subjects, StudytimeAdapter.Holder>(DiffCallback()) {

    @RequiresApi(Build.VERSION_CODES.O)
    private var viewModel: StudyTimeViewModel2 = StudyTimeViewModel2()

    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false
    private var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().reference

    interface TimerUpdateListener {
        fun onTimerUpdate(position: Int, time: Time)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = FragmentSubjecttimerBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Holder(binding)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.binding.playButton.setOnClickListener {
            if(isTimerRunning) {
                StopTimer()
            }else {
                StartTimer(data)
            }
        }
        val position = viewModel.getSubjectsList().value?.indexOf(data)
        println(position)
        if (position != null && position != -1) {
            viewModel.updateTime(position, data.time)
        }
        databaseReference.child(position.toString()).setValue(data)
    }

    private fun StartTimer(subject: Subjects) {
        countDownTimer = object: CountDownTimer(Long.MAX_VALUE, 1000){
            override fun onTick(millisUtilFinished: Long) {
                subject.time.sec++
                if (subject.time.sec >= 60) {
                    subject.time.sec = 0
                    subject.time.minute++
                }
                if (subject.time.minute >= 60) {
                    subject.time.minute = 0
                    subject.time.hour++
                }
                notifyDataSetChanged()

            }
            override fun onFinish() {}
            }.start()
            isTimerRunning = true
        }
    private fun StopTimer() {
        countDownTimer?.cancel()
        countDownTimer = null
        isTimerRunning = false
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



