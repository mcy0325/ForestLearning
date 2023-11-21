package com.example.forestlearning

import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentStudyTimeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class Study_timeFragment : Fragment() {

    private lateinit var binding: FragmentStudyTimeBinding
    @RequiresApi(Build.VERSION_CODES.O)
    private var viewModel: StudyTimeViewModel2 = StudyTimeViewModel2()
    private lateinit var adapter: StudytimeAdapter
    private lateinit var recyclerView: RecyclerView
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyTimeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        databaseReference = FirebaseDatabase.getInstance().getReference("subjects")

        viewModel = ViewModelProvider(requireActivity())[StudyTimeViewModel2::class.java]

        recyclerView = binding.studyTimeRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        adapter = StudytimeAdapter()
        recyclerView.adapter = adapter

        viewModel.subjectsLiveList.observe(viewLifecycleOwner, Observer { newList ->
            adapter.submitList(newList)
        })

        binding?.addButton?.setOnClickListener{
            findNavController().navigate(R.id.action_study_timeFragment_to_subject_adderFragment)
        }
    }


}