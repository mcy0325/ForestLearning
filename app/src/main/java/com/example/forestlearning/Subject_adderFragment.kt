package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentSubjectAdderBinding
import com.google.firebase.firestore.FirebaseFirestore


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Subject_adderFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class Subject_adderFragment : Fragment() {
    private lateinit var binding: FragmentSubjectAdderBinding
    private lateinit var spinner: Spinner
    private lateinit var viewModel: StudyTimeViewModel2
    val db: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSubjectAdderBinding.inflate(inflater, container, false)

        return binding?.root
    }

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

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
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
                viewModel.add_subject(temp)
                findNavController().navigate(R.id.action_subject_adderFragment_to_study_timeFragment)
            }

        }
    }

    private fun updateFruitImage(fruitName: String) {
        val fruitDrawable = when (fruitName) {
            "Apple" -> R.drawable.apple_resize
            "Banana" -> R.drawable.banana_resize
            "Grape" -> R.drawable.grape
            else -> R.drawable.apple_resize
        }
        binding?.fruitSelection?.setBackgroundResource(fruitDrawable)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Subject_adderFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Subject_adderFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}