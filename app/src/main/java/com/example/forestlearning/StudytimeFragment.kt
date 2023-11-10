package com.example.forestlearning

import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentStudytimeBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [StudytimeFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class StudytimeFragment : Fragment() {
    private lateinit var binding: FragmentStudytimeBinding
    private lateinit var viewModel: StudytimeViewModel
    private lateinit var recyclerView: RecyclerView


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

        recyclerView = binding.subjectScrollView

        viewModel = ViewModelProvider(requireActivity()).get(StudytimeViewModel::class.java)

        viewModel.getSubjectList().observe(viewLifecycleOwner) { subjectList ->
            val adapter = StudytimeAdapter(subjectList)
            recyclerView.adapter = adapter
        }

        return binding.root
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment studytimeFragment.
         */
    }
}