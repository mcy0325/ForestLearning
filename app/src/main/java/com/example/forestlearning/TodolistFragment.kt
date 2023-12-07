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
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class TodolistFragment : Fragment() { //투두 리스트 프래그먼트
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
        viewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        // RecyclerView 설정
        recyclerView = binding.todoRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoAdapter = TodoAdapter(viewModel)
        recyclerView.adapter = toDoAdapter

        // ViewModel에서 LiveData를 관찰하여 데이터 변경을 감지하고 UI 갱신
        viewModel.toDoItemList.observe(viewLifecycleOwner, Observer { newList ->
            toDoAdapter.submitList(newList)
        })
        //23.11.20
        // 선택된 날짜를 표시
        viewModel.selectedDateLiveData.observe(viewLifecycleOwner, Observer { date ->
            binding.selectedDate.text = date
        })

        // 데이터베이스에서 To-Do 아이템 불러오기
        loadTodoItems()

        binding?.addButton?.setOnClickListener {
            findNavController().navigate(R.id.action_todolistFragment_to_todoadderFragment)
        }
    }

    // TodoAddFragment에서 데이터를 받아와서 처리하는 메서드
    fun onTodoItemAdded(todoItem: Todo) {
        // 데이터를 Firebase에 저장
        val newTodoRef = databaseReference.push()
        newTodoRef.setValue(todoItem)

        // UI 갱신은 LiveData가 알아서 처리
    }

    private fun loadTodoItems() { //파이어베이스에서 Todo를 데려오고 뷰모델을 통해서 아이템을 갱신한다
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        //소름 수업에서 배웠던 let을 여기에 쓰네;;
        uid?.let { userUid ->
            //23.11.20 추가
            // ViewModel에서 선택된 날짜 가져오기
            val selectedDate = viewModel.getSelectedDate()
            val userDatabaseReference = databaseReference.child(userUid).orderByChild("date").equalTo(selectedDate)
            //val userDatabaseReference = databaseReference.child(userUid)

            userDatabaseReference.addValueEventListener(object : ValueEventListener {
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
                    Log.w(TAG, "데이터 읽는 것을 실패함", error.toException())
                }
            })
        }
    }

    /*
    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
     */
}