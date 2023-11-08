package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import com.example.forestlearning.databinding.FragmentCourseBinding

class CourseFragment : Fragment() {

    private var _binding: FragmentCourseBinding? = null
    private val binding get() = _binding!!

    private lateinit var yearAdapter: ArrayAdapter<String>
    private lateinit var yearSpinner: Spinner
    private lateinit var termAdapter: ArrayAdapter<String>
    private lateinit var termSpinner: Spinner
    private lateinit var areaAdapter: ArrayAdapter<String>
    private lateinit var areaSpinner: Spinner

    private var courseYear: String = ""
    private var courseTerm: String = ""
    private var courseArea: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val courseUniversityGroup = view?.findViewById<RadioGroup>(R.id.courseUniversityGroup)
        yearSpinner = view?.findViewById(R.id.yearSpinner)!!
        termSpinner = view?.findViewById(R.id.termSpinner)!!
        areaSpinner = view?.findViewById(R.id.areaSpinner)!!

        courseUniversityGroup?.setOnCheckedChangeListener { radioGroup, checkedId ->
            val courseButton = view?.findViewById<RadioButton>(checkedId)
            val courseUniversity = courseButton?.text.toString()

            val yearAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = yearAdapter

            val termAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item)
            termSpinner.adapter = termAdapter

            if(courseUniversity=="전공") {
                val areaAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.majorArea, android.R.layout.simple_spinner_dropdown_item)
                areaSpinner.adapter = areaAdapter
            }

            else if(courseUniversity=="교양") {
                val areaAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.liberalArea, android.R.layout.simple_spinner_dropdown_item)
                areaSpinner.adapter = areaAdapter
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCourseBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





