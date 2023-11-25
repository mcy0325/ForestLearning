//투두캘린더프래그먼트
package com.example.forestlearning

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.ActivityMainBinding
import com.example.forestlearning.databinding.FragmentCalendarBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class CalendarFragment : Fragment() {
    var binding: FragmentCalendarBinding? = null

    //var dayText: String? = null
    //23.11.20 추가
    private lateinit var todoViewModel: TodoViewModel
    //23.11.21 추가
    private lateinit var recyclerView: RecyclerView
    private lateinit var toDoAdapter: TodoAdapter
    private lateinit var databaseReference: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // View Binding을 통해 캘린더 뷰와 텍스트뷰 초기화
        //객체 생성
        val dayText = binding?.dayText
        val calendarView = binding?.calendarView

        //날짜 형태
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년MM월dd일")

        //date타입
        val date: Date = Date(calendarView!!.date)

        //현재 날짜 담기
        dayText?.text = dateFormat.format(date)

        //23.11.20 추가
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            todoViewModel.setSelectedDate(selectedDate)
            loadTodoItems()
        }

        //23.11.21 추가
        // Firebase 데이터베이스 참조
        databaseReference = FirebaseDatabase.getInstance().getReference("todos")

        // ViewModel 초기화
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)

        // RecyclerView 설정
        recyclerView = binding!!.CalendarRecyclerView
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        toDoAdapter = TodoAdapter(todoViewModel)
        recyclerView.adapter = toDoAdapter

        // ViewModel에서 LiveData를 관찰하여 데이터 변경을 감지하고 UI 갱신
        todoViewModel.toDoItemList.observe(viewLifecycleOwner, Observer { newList ->
            toDoAdapter.submitList(newList)
        })

        //23.11.21
        // 선택된 날짜를 표시
        todoViewModel.selectedDateLiveData.observe(viewLifecycleOwner, Observer { date ->
            binding?.dayText?.text = date
        })

        // 데이터베이스에서 To-Do 아이템 불러오기
        loadTodoItems()
    }
    //23.11.21 추가
    private fun loadTodoItems() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
        //소름 수업에서 배웠던 let을 여기에 쓰네;;
        uid?.let { userUid ->
            //23.11.20 추가
            // ViewModel에서 선택된 날짜 가져오기
            val selectedDate = todoViewModel.getSelectedDate()
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
                    todoViewModel.updateTodoItems(toDoItems)
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w(ContentValues.TAG, "Failed to read value.", error.toException())
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}