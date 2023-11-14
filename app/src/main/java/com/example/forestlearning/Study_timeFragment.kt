package com.example.forestlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentStudyTimeBinding

class Study_timeFragment : Fragment() {
    companion object {
        fun newInstance() = Study_timeFragment()
    }

    private lateinit var binding: FragmentStudyTimeBinding
    private lateinit var viewModel: StudyTimeViewModel2

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyTimeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(StudyTimeViewModel2::class.java)
        // TODO: Use the ViewModel
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.addButton?.setOnClickListener{
            findNavController().navigate(R.id.action_study_timeFragment_to_subject_adderFragment)
        }
    }

}