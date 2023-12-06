package com.example.forestlearning
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding

class StudytimeAdapter()
    : ListAdapter<Subjects, StudytimeAdapter.Holder>(DiffCallback()) {

    @RequiresApi(Build.VERSION_CODES.O)
    private var viewModel: StudyTimeViewModel2 = StudyTimeViewModel2()

    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    private var isRunning = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding =
            FragmentSubjecttimerBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return Holder(binding)
    }

    // position의 data를 subjecttimer에 업로드
    // 해당 position에서 하게 될 시간 측정 로직 준비
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: Holder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
        holder.binding.playButton.setOnClickListener {
            // 시간 측정 로직 작성
            if (position != -1) {
                if(!isRunning) {
                    startTimer(position, data)
                    isRunning = true
                }else {
                    stopTimer(position, data)
                    isRunning = false
                }
            }
        }
    }

    // startTimer : timer를 작동시켜 매 초마다 viewmodel 업데이트
    // time이 업데이트 되어 일정 시간이 될때마다 fruit도 증가
    // stopTimer : timer 중지
    @RequiresApi(Build.VERSION_CODES.O)
    private fun startTimer(position: Int, subject: Subjects) {
        runnable = Runnable {
            subject.time.sec++
            if (subject.time.sec == 60) {
                subject.time.sec = 0
                subject.time.minute++
            }
            if (subject.time.minute == 60) {
                subject.time.minute = 0
                subject.time.hour++
            }
            viewModel.updateTime(position, subject.time)
            viewModel.updatetotaltime()
            viewModel.update_todaytreefruit(position, subject.time.hour)
            handler.postDelayed(runnable, 1000)
        }
        handler.postDelayed(runnable, 1000)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    private fun stopTimer(position: Int, subject: Subjects) {
        handler.removeCallbacks(runnable)
        viewModel.updateTime(position, subject.time)
        viewModel.updatetotaltime()
    }

    // Holder, bind를 통해 subjecttimerBinding의 view를 통일화
    class Holder(val binding: FragmentSubjecttimerBinding) : RecyclerView.ViewHolder(binding.root) {
        private fun formatTime(time: Time): String {
            return String.format("%02d:%02d:%02d", time.hour, time.minute, time.sec)
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

    // DiffCallback : ListAdapter의 데이터가 변경될 때 데이터의 동일성을 파악해 애니메이션 및 성능 향상
    private class DiffCallback : DiffUtil.ItemCallback<Subjects>() {
        override fun areItemsTheSame(oldItem: Subjects, newItem: Subjects): Boolean {
            return oldItem.name == newItem.name
        }
        override fun areContentsTheSame(oldItem: Subjects, newItem: Subjects): Boolean {
            return oldItem == newItem
        }
    }
}


