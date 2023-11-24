package com.example.forestlearning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentCourseAddBinding
import com.example.forestlearning.viewmodel.TimeTableViewModel

class CourseAddFragment : Fragment() {
    //뷰 바인딩 초기화
    private var _binding: FragmentCourseAddBinding? = null
    private val binding get() = _binding
    val viewModel : TimeTableViewModel by activityViewModels()

    //스피너 변수 선언
    private var daySpinners: List<Spinner>? = null
    private var timeSpinners: List<Spinner>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //뷰 바인딩 설정
        _binding = FragmentCourseAddBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //스피너 바인딩
        daySpinners = listOfNotNull(binding?.daySpinner1, binding?.daySpinner2)
        timeSpinners = listOfNotNull(binding?.timeSpinner1, binding?.timeSpinner2, binding?.timeSpinner3, binding?.timeSpinner4)

        //courseEndButton 클릭 시 동작
        binding?.courseEndButton?.setOnClickListener {
            // 강의 정보를 설정
            viewModel.setCourseData(
                binding?.courseName?.text.toString(),
                binding?.teacherName?.text.toString(),
                daySpinners?.getOrNull(0)?.selectedItem.toString(),
                timeSpinners?.getOrNull(0)?.selectedItem.toString(),
                timeSpinners?.getOrNull(1)?.selectedItem.toString(),
                binding?.coursePlace1?.text.toString(),
                daySpinners?.getOrNull(1)?.selectedItem.toString(),
                timeSpinners?.getOrNull(2)?.selectedItem.toString(),
                timeSpinners?.getOrNull(3)?.selectedItem.toString(),
                binding?.coursePlace2?.text.toString())

            // timetableFragment로 이동
            findNavController().navigate(R.id.action_courseAddFragment_to_timetableFragment)
        }

        //스피너에 어댑터 설정
        val dayAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.days, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter = ArrayAdapter.createFromResource(requireContext(), R.array.time, android.R.layout.simple_spinner_dropdown_item)

        // 각각의 스피너에 어댑터 연결
        daySpinners?.forEach { it?.adapter = dayAdapter }
        timeSpinners?.forEach { it?.adapter = timeAdapter }

    }

    // 뷰가 파괴될 때 바인딩 해제
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}