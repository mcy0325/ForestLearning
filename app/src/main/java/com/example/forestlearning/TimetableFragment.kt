package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentTimetableBinding
import com.example.forestlearning.viewmodel.TimeTableViewModel
import com.google.firebase.Firebase
import com.google.firebase.auth.auth

class TimetableFragment : Fragment() {

    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding
    val viewModel : TimeTableViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayTimeMap = mapOf(
            Pair("월09:00-10:00", binding?.monday0),
            Pair("월10:00-11:00", binding?.monday1),
            Pair("월11:00-12:00", binding?.monday2),
            Pair("월12:00:13:00", binding?.monday3),
            Pair("월13:00-14:00", binding?.monday4),
            Pair("월14:00-15:00", binding?.monday5),
            Pair("월15:00-16:00", binding?.monday6),
            Pair("월16:00-17:00", binding?.monday7),
            Pair("월17:00-18:00", binding?.monday8),
            Pair("화09:00-10:00", binding?.tuesday0),
            Pair("화10:00-11:00", binding?.tuesday1),
            Pair("화11:00-12:00", binding?.tuesday2),
            Pair("화12:00:13:00", binding?.tuesday3),
            Pair("화13:00-14:00", binding?.tuesday4),
            Pair("화14:00-15:00", binding?.tuesday5),
            Pair("화15:00-16:00", binding?.tuesday6),
            Pair("화16:00-17:00", binding?.tuesday7),
            Pair("화17:00-18:00", binding?.tuesday8),
            Pair("수09:00-10:00", binding?.wednesday0),
            Pair("수10:00-11:00", binding?.wednesday1),
            Pair("수11:00-12:00", binding?.wednesday2),
            Pair("수12:00:13:00", binding?.wednesday3),
            Pair("수13:00-14:00", binding?.wednesday4),
            Pair("수14:00-15:00", binding?.wednesday5),
            Pair("수15:00-16:00", binding?.wednesday6),
            Pair("수16:00-17:00", binding?.wednesday7),
            Pair("수17:00-18:00", binding?.wednesday8),
            Pair("목09:00-10:00", binding?.thursday0),
            Pair("목10:00-11:00", binding?.thursday1),
            Pair("목11:00-12:00", binding?.thursday2),
            Pair("목12:00:13:00", binding?.thursday3),
            Pair("목13:00-14:00", binding?.thursday4),
            Pair("목14:00-15:00", binding?.thursday5),
            Pair("목15:00-16:00", binding?.thursday6),
            Pair("목16:00-17:00", binding?.thursday7),
            Pair("목17:00-18:00", binding?.thursday8),
            Pair("금09:00-10:00", binding?.friday0),
            Pair("금10:00-11:00", binding?.friday1),
            Pair("금11:00-12:00", binding?.friday2),
            Pair("금12:00:13:00", binding?.friday3),
            Pair("금13:00-14:00", binding?.friday4),
            Pair("금14:00-15:00", binding?.friday5),
            Pair("금15:00-16:00", binding?.friday6),
            Pair("금16:00-17:00", binding?.friday7),
            Pair("금17:00-18:00", binding?.friday8)
        )

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            courses.forEach { course ->
                // 첫 번째 시간대의 TextView 업데이트
                val key1 = "${course.day1}${course.time1}-${course.time2}"
                val textView1 = dayTimeMap[key1]
                textView1?.text = "${course.courseName}\n${course.teacherName}\n${course.coursePlace1}"

                // 두 번째 시간대의 TextView 업데이트
                val key2 = "${course.day2}${course.time3}-${course.time4}"
                val textView2 = dayTimeMap[key2]
                textView2?.text = "${course.courseName}\n${course.teacherName}\n${course.coursePlace2}"
            }
        }

        //courseAddButton 클릭 시 courseAddFragment로 이동
        binding?.courseAddButton?.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_courseAddFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}