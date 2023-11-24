package com.example.forestlearning

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentSubjectAdderBinding
import com.google.firebase.firestore.FirebaseFirestore

class Subject_adderFragment : Fragment() {
    private lateinit var binding: FragmentSubjectAdderBinding
    private lateinit var spinner: Spinner

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSubjectAdderBinding.inflate(inflater, container, false)

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        spinner = binding.fruitSelection

        spinner.onItemSelectedListener = object : AdapterView
                .OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedFruit = parent?.getItemAtPosition(position).toString()
                updateFruitImage(selectedFruit)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
                }

        binding.addButtonSubject.setOnClickListener {
            if (binding.subjectInfoInput.text?.isEmpty() == true ||
                binding.subjectInfoInput.text?.isEmpty() == true) {
                Toast.makeText(requireContext(), "과목명과 info을 입력해주세요.", Toast.LENGTH_SHORT).show()
            } else {
                val viewModel = ViewModelProvider(requireActivity()).get(StudyTimeViewModel2::class.java)

                val name :String = binding.subjectInfoInput.text.toString()
                val info :String = binding.subjectInfoInput.text.toString()
                val fruit :Int = when(binding?.fruitSelection?.selectedItem.toString()) {
                    "Apple" -> 1
                    "Banana" -> 2
                    "Grape" -> 3
                    else -> 1
                }
                val temp = Subjects(name, info, fruit, Time(0,0,0))
                viewModel.addSubjects(temp)
                findNavController().navigate(R.id.action_subject_adderFragment_to_study_timeFragment)
            } } }
    private fun updateFruitImage(fruitName: String) {
        val fruitDrawable = when (fruitName) {
            "Apple" -> R.drawable.apple_resize
            "Banana" -> R.drawable.banana_resize
            "Grape" -> R.drawable.grape
            else -> R.drawable.apple_resize
        }
        binding?.fruitSelection?.setBackgroundResource(fruitDrawable)
    } }