package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentCourseAddBinding
import com.example.forestlearning.databinding.FragmentLoginBinding

class CourseAddFragment : Fragment() {

    private var _binding: FragmentCourseAddBinding? = null
    private val binding get() = _binding!!

    private lateinit var daySpinner1: Spinner
    private lateinit var daySpinner2: Spinner
    private lateinit var timeSpinner1: Spinner
    private lateinit var timeSpinner2: Spinner

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseAddBinding.inflate(inflater, container, false)

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //courseEndButton 클릭 시 timetableFragment로 이동
        binding.courseEndButton.setOnClickListener {
            findNavController().navigate(R.id.action_courseAddFragment_to_timetableFragment)
        }


        daySpinner1 = view.findViewById(R.id.daySpinner1)!!
        daySpinner2 = view.findViewById(R.id.daySpinner2)!!
        timeSpinner1 = view.findViewById(R.id.timeSpinner1)!!
        timeSpinner2 = view.findViewById(R.id.timeSpinner2)!!

        val dayAdapter1 = ArrayAdapter.createFromResource(requireActivity(), R.array.days, android.R.layout.simple_spinner_dropdown_item)
        val dayAdapter2 = ArrayAdapter.createFromResource(requireActivity(), R.array.days, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter1 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)
        val timeAdapter2 = ArrayAdapter.createFromResource(requireActivity(), R.array.time, android.R.layout.simple_spinner_dropdown_item)

        daySpinner1.adapter = dayAdapter1
        daySpinner2.adapter = dayAdapter2
        timeSpinner1.adapter = timeAdapter1
        timeSpinner2.adapter = timeAdapter2

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}