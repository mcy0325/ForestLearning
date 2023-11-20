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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubjecttimerFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubjecttimerFragment : Fragment(){
    private var countDownTimer: CountDownTimer? = null
    private var isTimerRunning = false

    private lateinit var binding: FragmentSubjecttimerBinding
    private var viewModel: StudyTimeViewModel2 = StudyTimeViewModel2()
    private lateinit var StudytimeAdapter: StudytimeAdapter
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
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
        timerstate(this.viewModel.getSubjectsList().value!![0])
    }


    private fun timerstate(subject: Subjects) {
        binding.playButton.setOnClickListener {
            if (isTimerRunning) {
                countDownTimer?.cancel()
                isTimerRunning = false
            } else {
                startTimer(subject)
                isTimerRunning = true
            }
        }
    }

    private fun startTimer(subject: Subjects) {
        val timer = object : CountDownTimer(3600000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = millisUntilFinished / 1000
                val hours = seconds / 3600
                val minutes = (seconds % 3600) / 60
                val remainingSeconds = seconds % 60

                val time = Time(hours, minutes, remainingSeconds)

                subject.update_time(time)
                StudytimeAdapter.notifyDataSetChanged()
            }

            override fun onFinish() {

            }
        }
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment subjecttimerFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubjecttimerFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}