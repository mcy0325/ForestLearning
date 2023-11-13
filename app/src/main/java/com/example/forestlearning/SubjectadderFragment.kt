package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Spinner
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
    private lateinit var spinner: Spinner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentSubjectadderBinding.inflate(inflater, container, false)

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
                updateFruitImage("Apple")
            }
                }


        binding?.subjectAdderButton?.setOnClickListener {
            val name = binding?.subjectName?.text.toString()
            val info = binding?.subjectInfo?.text.toString()

        }

    }
    private fun updateFruitImage(selectedFruit: String) {
        val drawableId = when (selectedFruit) {
            "Apple" -> R.drawable.apple
            "Banana" -> R.drawable.banana
            "Grape" -> R.drawable.grape
            else -> R.drawable.apple
        }

        binding?.fruitSelection?.setBackgroundResource(drawableId)
    }




}