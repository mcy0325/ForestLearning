package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.forestlearning.databinding.FragmentStudytimeBinding


class StudytimeFragment : Fragment() {
    private lateinit var binding: FragmentStudytimeBinding
    private lateinit var viewModel: StudytimeViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentStudytimeBinding.inflate(inflater, container, false)

        binding.addbutton.setOnClickListener {
            val subjectadderFragment = SubjectadderFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.subject_adder_view, subjectadderFragment)
                .addToBackStack(null)
                .commit()
        }



        return binding.root
    }
}