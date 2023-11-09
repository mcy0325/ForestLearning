package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.forestlearning.databinding.FragmentSubjectadderBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SubjectadderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class SubjectadderFragment : Fragment() {
    private lateinit var binding: FragmentSubjectadderBinding
    private lateinit var viewModel: StudytimeViewModel

    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSubjectadderBinding.inflate(inflater, container, false)

        viewModel = ViewModelProvider(requireActivity()).get(StudytimeViewModel::class.java)

        fruit_selection()

        binding.subjectAdderButton.setOnClickListener {
            val name = binding.subjectName.text.toString()
            val info = binding.subjectInfo.text.toString()

            add_subject(name, info)
        }



        return binding.root
    }

    private fun add_subject(name: String, info: String) {
        val subject = Subjects(name, info)

        viewModel.setSubject(subject)
        fruit_check()
    }


    private fun fruit_check() {
        when (binding.fruitSelection.id) {
            R.id.apple -> {
                viewModel.set_fruit(1)
            }
            R.id.banana -> {
                viewModel.set_fruit(2)
            }
            R.id.grape -> {
                viewModel.set_fruit(3)
            }
        }
    }


    private fun fruit_selection() {
        binding.fruitSelection.setOnClickListener {

            val fruitSelectionFragment = FruitSelectionFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fruit_selection_view, fruitSelectionFragment)
                .addToBackStack(null)
                .commit()
        }
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SubjectadderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SubjectadderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}