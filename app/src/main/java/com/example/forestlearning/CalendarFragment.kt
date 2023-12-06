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
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년 MM월 dd일") //날짜 형태를 수정
        val date: Date = Date(calendarView!!.date) //date타입

        dayText?.text = dateFormat.format(date)
        //데이터 클래스를 사용해 현재 날짜를 가져오고 정해둔 형식에 맞춰서 텍스트뷰에 표시

        //23.11.20 추가
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        //데이터를 가지는 투두 뷰모델을 다른 프래그먼트에서도 사용할 수 있도록 초기화

        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = "${year}년 ${month + 1}월 ${dayOfMonth}일"
            todoViewModel.setSelectedDate(selectedDate)
            loadTodoItems()
        } //캘린더뷰의 선택된 날짜를 뷰모델로 옮겨주고, 기존의 투두 아이템들을 데려옴

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

        // ViewModel에서 toDoItemList의 LiveData를 관찰하여 데이터가 변경되면 옵저브 -> UI 갱신
        todoViewModel.toDoItemList.observe(viewLifecycleOwner, Observer { newList ->
            toDoAdapter.submitList(newList)
        })

        //23.11.21
        // calendarfragment의 텍스트 뷰 선택된 날짜를 표시
        todoViewModel.selectedDateLiveData.observe(viewLifecycleOwner, Observer { date ->
            binding?.dayText?.text = date
        })

        // 데이터베이스에서 To-Do 아이템 불러오기
        loadTodoItems()
    }
    //23.11.21 추가
    private fun loadTodoItems() { //선택된 날짜의 Todo를 가져와 UI갱신
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid
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
                    Log.w(ContentValues.TAG, "데이터 읽는 것을 실패함", error.toException())
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}