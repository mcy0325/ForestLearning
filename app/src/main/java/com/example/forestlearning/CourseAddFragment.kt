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

    private var _binding: FragmentCourseAddBinding? = null
    private val binding get() = _binding
    val viewModel : TimeTableViewModel by activityViewModels()

    private lateinit var daySpinner1: Spinner
    private lateinit var daySpinner2: Spinner
    private lateinit var timeSpinner1: Spinner
    private lateinit var timeSpinner2: Spinner
    private lateinit var timeSpinner3: Spinner
    private lateinit var timeSpinner4: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseAddBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        daySpinner1 = binding?.daySpinner1 ?: return
        daySpinner2 = binding?.daySpinner2 ?: return
        timeSpinner1 = binding?.timeSpinner1 ?: return
        timeSpinner2 = binding?.timeSpinner2 ?: return
        timeSpinner3 = binding?.timeSpinner3 ?: return
        timeSpinner4 = binding?.timeSpinner4 ?: return

        //courseEndButton 클릭 시 timetableFragment로 이동
        binding?.courseEndButton?.setOnClickListener {
            findNavController().navigate(R.id.action_courseAddFragment_to_timetableFragment)

            viewModel.setCourseData(
                binding?.courseName?.text.toString(),
                binding?.teacherName?.text.toString(),
                daySpinner1.selectedItem.toString(),
                timeSpinner1.selectedItem.toString(),
                timeSpinner2.selectedItem.toString(),
                binding?.coursePlace1?.text.toString(),
                daySpinner2.selectedItem.toString(),
                timeSpinner3.selectedItem.toString(),
                timeSpinner4.selectedItem.toString(),
                binding?.coursePlace2?.text.toString())
        }

        val dayAdapter1 = ArrayAdapter.createFromResource(requireActivity(), R.array.days, android.R.layout.simple_spinner_dropdown_item)
        val dayAdapter2 = ArrayAdapter.createFromResource(requireActivity(), R.array.days, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter1 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter2 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter3 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter4 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)

        daySpinner1.adapter = dayAdapter1
        daySpinner2.adapter = dayAdapter2
        timeSpinner1.adapter = timeAdapter1
        timeSpinner2.adapter = timeAdapter2
        timeSpinner3.adapter = timeAdapter3
        timeSpinner4.adapter = timeAdapter4

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}