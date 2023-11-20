package com.example.forestlearning

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.forestlearning.databinding.FragmentSubjecttimerBinding
import kotlin.concurrent.timer

class SubjecttimerFragment : Fragment(){
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    private lateinit var binding: FragmentSubjecttimerBinding
    private var viewModel: StudyTimeViewModel2 = StudyTimeViewModel2()
    private lateinit var StudytimeAdapter: StudytimeAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSubjecttimerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProvider(requireActivity())[StudyTimeViewModel2::class.java]
        timerstate()
    }


    private fun timerstate() {
        binding.playButton.setOnClickListener {
            if (isTimerRunning) {
                countDownTimer?.cancel()
                isTimerRunning = false
            } else {
                startTimer()
                isTimerRunning = true
            }
        }
    }

    private fun startTimer(){
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val remainingSeconds = seconds % 60

                val time = Time(hours, minutes, remainingSeconds)

                viewModel.updatecurrenttime(time)
            }
            override fun onFinish() {

            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        countDownTimer?.cancel()
    }


}