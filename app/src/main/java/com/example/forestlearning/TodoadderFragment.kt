package com.example.forestlearning

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.forestlearning.databinding.FragmentTodoadderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class TodoadderFragment : Fragment() { //사용자가 데이터 추가하는 프래그먼트
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

        // 현재 로그인된 사용자의 UID 가져오기
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        // UID가 null이 아닌 경우에만 데이터베이스 경로 생성
        uid?.let { userUid ->
            // 사용자 별로 투두를 저장할 경로 설정
            databaseReference = FirebaseDatabase.getInstance().getReference("todos").child(userUid)

            val saveButton = binding.saveButton
            saveButton.setOnClickListener {
                val title = binding.titleEditText.text.toString()

                // ToDo아이템을 TodoAddFragment에서 받아오기
                val newTodoItem = Todo(content = title)

                // Firebase Realtime Database에 데이터 업로드
                val newTodoRef = databaseReference.push()
                newTodoRef.setValue(newTodoItem)

                // TodoAddFragment를 스택에서 제거하여 이전 화면으로 돌아가기
                parentFragmentManager.popBackStack()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}