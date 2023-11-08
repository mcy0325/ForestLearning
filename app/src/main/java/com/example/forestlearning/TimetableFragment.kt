package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentTimetableBinding

class TimetableFragment : Fragment() {

    private var _binding: FragmentTimetableBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.courseButton.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_courseFragment)
        }

        // navigation 폴더 안에 있는 nav_main.xml에서 action을 만들기
        // action = fragment와 fragment 끼리 연결
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}