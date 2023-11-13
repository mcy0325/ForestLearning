package com.example.forestlearning

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.FragmentTodolistBinding
import com.example.forestlearning.databinding.FragmentTodoadderBinding
import com.google.firebase.database.*

class TodolistFragment : Fragment() { //투두
    private lateinit var viewModel: TodoViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var toDoAdapter: TodoAdapter
    private lateinit var databaseReference: DatabaseReference
    private lateinit var binding: FragmentTodolistBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? { //바인딩
        binding = FragmentTodolistBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Firebase 데이터베이스 참조
        databaseReference = FirebaseDatabase.getInstance().getReference("todos")

        // ViewModel 초기화
        viewModel = ViewModelProvider(this).get(TodoViewModel::class.java)

        // RecyclerView 설정
        recyclerView = binding.todoRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoAdapter = TodoAdapter()
        recyclerView.adapter = toDoAdapter

        // ViewModel에서 LiveData를 관찰하여 데이터 변경을 감지하고 UI 갱신
        viewModel.toDoItemList.observe(viewLifecycleOwner, Observer { newList ->
            toDoAdapter.submitList(newList)
        })

        // 데이터베이스에서 To-Do 아이템 불러오기
        loadTodoItems()

        binding?.addButton?.setOnClickListener {
            // TodoAddFragment로 이동
            /*
            val todoAddFragment = TodoAddFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.todolistFragment, todoAddFragment)
                .addToBackStack(null)
                .commit()
        }
             */
            findNavController().navigate(R.id.action_todolistFragment_to_todoadderFragment)
        }

        /*
    fun onFabCliked() {
        val todo = Todo(false, "개어려워")
        todoViewModel.addTodo(todo)
    }

 */
    }

    // TodoAddFragment에서 데이터를 받아와서 처리하는 메서드
    fun onTodoItemAdded(todoItem: Todo) {
        // 데이터를 ViewModel에 추가
        viewModel.addTodoItem(todoItem)

        // RecyclerView 갱신
        toDoAdapter.notifyDataSetChanged()
    }
    private fun loadTodoItems() {
        // 데이터베이스에서 To-Do 아이템 목록 불러오기
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val toDoItems = mutableListOf<Todo>()

                for (snapshot in dataSnapshot.children) {
                    val todo = snapshot.getValue(Todo::class.java)
                    if (todo != null) {
                        toDoItems.add(todo)
                    }
                }

                // ViewModel을 통해 데이터 갱신
                viewModel.updateTodoItems(toDoItems)
            }

            override fun onCancelled(error: DatabaseError) {
                Log.w(TAG, "Failed to read value.", error.toException())
            }
        })
    }
}