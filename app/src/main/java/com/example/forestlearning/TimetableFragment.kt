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

            println("In TimeTableFragment Data Change Observed!!")

            tempCourseData.setData(it.courseName, it.teacherName, it.day1, it.time1, it.time2, it.day2, it.time3, it.time4)
            if(tempCourseData.day1 == "월" && tempCourseData.time1 == "09:00" && tempCourseData.time2 == "10:00") {
                binding?.monday0?.text = tempCourseData.courseName
                println("monday0: ${binding?.monday0?.text}")
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