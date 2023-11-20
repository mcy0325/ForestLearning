package com.example.forestlearning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.forestlearning.databinding.FragmentTodoadderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TodoadderFragment : Fragment() { //사용자가 데이터 추가하는 프래그먼트
    private var _binding: FragmentTodoadderBinding? = null
    private val binding get() = _binding!!
    //추가
    private lateinit var databaseReference: DatabaseReference
    //23.11.20 추가
    private lateinit var todoViewModel: TodoViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentTodoadderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //23.11.20 추가
        // ViewModel 초기화
        todoViewModel = ViewModelProvider(requireActivity()).get(TodoViewModel::class.java)
        // 현재 로그인된 사용자의 UID 가져오기
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        // UID가 null이 아닌 경우에만 데이터베이스 경로 생성
        uid?.let { userUid ->
            // 사용자 별로 투두를 저장할 경로 설정
            databaseReference = FirebaseDatabase.getInstance().getReference("todos").child(userUid)

            //val saveButton = binding.saveButton
            //saveButton.setOnClickListener {
            binding.saveButton.setOnClickListener {
                val title = binding.titleEditText.text.toString()
                //23.11.20 추가
                val date = todoViewModel.getSelectedDate()
                // ToDo아이템을 TodoAdderFragment에서 받아오기
                val newTodoItem = Todo(content = title, date = date)


                //23.11.20 추가
                // 선택된 날짜에 대한 투두 아이템 추가
                todoViewModel.addTodoItemForSelectedDate(newTodoItem)

                // Firebase Realtime Database에 데이터 업로드
                val newTodoRef = databaseReference.push()
                newTodoRef.setValue(newTodoItem)

                // TodoadderFragment를 스택에서 제거하여 이전 화면으로 돌아가기
                parentFragmentManager.popBackStack()
                /*


                // Firebase Realtime Database에 데이터 업로드
                val newTodoRef = databaseReference.push()
                newTodoRef.setValue(newTodoItem)

                // TodoAddFragment를 스택에서 제거하여 이전 화면으로 돌아가기
                parentFragmentManager.popBackStack()

                 */
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
