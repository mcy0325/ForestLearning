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

class TimetableFragment : Fragment() {

    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding
    val viewModel : TimeTableViewModel by activityViewModels()
    private val tempCourseData : CourseData = CourseData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.courseData.observe(viewLifecycleOwner) {
            println("In TimeTabeFragment Data Change Observed!!")

            tempCourseData.setData(it.courseName, it.teacherName, it.day1, it.time1, it.day2, it.time2)
            println("CourseName: ${tempCourseData.courseName}")
            println("TeacherName: ${tempCourseData.teacherName}")
            println("Day1: ${tempCourseData.day1}")
            println("Time1: ${tempCourseData.time1}")
            println("Day2: ${tempCourseData.day2}")
            println("Time2: ${tempCourseData.time2}")
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