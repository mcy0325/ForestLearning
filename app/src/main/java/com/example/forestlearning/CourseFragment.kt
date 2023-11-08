package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Spinner
import androidx.navigation.fragment.findNavController
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
    private lateinit var majorAdapter: ArrayAdapter<String>
    private lateinit var majorSpinner: Spinner

    private var courseYear: String = ""
    private var courseTerm: String = ""
    private var courseArea: String = ""

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val courseUniversityGroup = view?.findViewById<RadioGroup>(R.id.courseUniversityGroup)
        yearSpinner = view?.findViewById(R.id.yearSpinner)!!
        termSpinner = view?.findViewById(R.id.termSpinner)!!
        areaSpinner = view?.findViewById(R.id.areaSpinner)!!
        majorSpinner = view?.findViewById(R.id.majorSpinner)!!

        courseUniversityGroup?.setOnCheckedChangeListener { _, checkedId ->
            val courseButton = view?.findViewById<RadioButton>(checkedId)
            val courseUniversity = courseButton?.text.toString()

            val yearAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.year, android.R.layout.simple_spinner_dropdown_item)
            yearSpinner.adapter = yearAdapter

            val termAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.term, android.R.layout.simple_spinner_dropdown_item)
            termSpinner.adapter = termAdapter

            if(courseUniversity=="학부") {
                val areaAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.universityArea, android.R.layout.simple_spinner_dropdown_item)
                areaSpinner.adapter = areaAdapter
                val majorAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item)
                majorSpinner.adapter = majorAdapter
            }

            else if(courseUniversity=="대학원") {
                val areaAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.graduateArea, android.R.layout.simple_spinner_dropdown_item)
                areaSpinner.adapter = areaAdapter
                val majorAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item)
                majorSpinner.adapter = majorAdapter
            }

            areaSpinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if(areaSpinner.selectedItem == "교양및기타") {
                        val majorAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.universityRefinementMajor, android.R.layout.simple_spinner_dropdown_item)
                        majorSpinner.adapter = majorAdapter
                    }

                    if(areaSpinner.selectedItem == "전공") {
                        val majorAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.universityMajor, android.R.layout.simple_spinner_dropdown_item)
                        majorSpinner.adapter = majorAdapter
                    }

                    if(areaSpinner.selectedItem == "대학원전공준비중") {
                        val majorAdapter = ArrayAdapter.createFromResource(requireActivity(), R.array.graduateMajor, android.R.layout.simple_spinner_dropdown_item)
                        majorSpinner.adapter = majorAdapter
                    }
                }

                override fun onNothingSelected(adapterView: AdapterView<*>?) {
                    // 이 메서드는 아무것도 선택되지 않았을 때 호출됩니다.
                    // 필요한 경우 여기에 코드를 추가할 수 있습니다.
                }
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.backButton.setOnClickListener {
            findNavController().navigate(R.id.action_courseFragment_to_timetableFragment)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}





