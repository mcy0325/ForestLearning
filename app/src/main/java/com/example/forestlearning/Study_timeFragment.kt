package com.example.forestlearning

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forestlearning.databinding.FragmentStudyTimeBinding

class Study_timeFragment : Fragment() {
    companion object {
        fun newInstance() = Study_timeFragment()
    }

    private lateinit var binding: FragmentStudyTimeBinding
    private val viewModel: StudyTimeViewModel2 by lazy {
        ViewModelProvider(this)[StudyTimeViewModel2::class.java]
    }
    private lateinit var adapter: StudytimeAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyTimeBinding.inflate(inflater, container, false)

        adapter = StudytimeAdapter(mutableListOf())
        binding.studyTimeRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.studyTimeRecyclerView.adapter = adapter
        viewModel.updateSujectsFromFirebase()


        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.updateSujectsFromFirebase().observe(viewLifecycleOwner, Observer {
            adapter.updateData(it)
            adapter.notifyDataSetChanged()
        })


        binding?.addButton?.setOnClickListener{
            findNavController().navigate(R.id.action_study_timeFragment_to_subject_adderFragment)
        }
    }

}