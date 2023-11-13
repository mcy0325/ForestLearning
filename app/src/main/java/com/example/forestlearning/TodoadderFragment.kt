package com.example.forestlearning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.forestlearning.databinding.FragmentTodoadderBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TodoadderFragment : Fragment() {
    private var _binding: FragmentTodoadderBinding? = null
    private val binding get() = _binding!!
    //추가
    private lateinit var databaseReference: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoadderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        databaseReference = FirebaseDatabase.getInstance().getReference("todos")

        val saveButton = binding.saveButton
        saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString()

            // ToDo아이템을 TodoAddFragment에서 받아오기
            val newTodoItem = Todo(content = title)
            // Firebase Realtime Database에 데이터 업로드
            val newTodoRef = databaseReference.push()
            newTodoRef.setValue(newTodoItem)

            /*
            // 부모 Fragment에 데이터 전달
            val parentFragment = parentFragment
            if (parentFragment is TodolistFragment) {
                parentFragment.onTodoItemAdded(newTodoItem)
            }
             */
            // TodoAddFragment를 스택에서 제거하여 이전 화면으로 돌아가기
            parentFragmentManager.popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
