package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentLoginBinding
import com.example.forestlearning.Authentication.Companion.auth
import com.example.forestlearning.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null
    val viewModel: UserViewModel by activityViewModels()
    val db : FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(inflater, container, false)

        //회원가입 버튼을 누르면 회원가입 화면으로 이동
        binding?.signInButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signInFragment2)
        }

        //로그인 버튼 이벤트
        binding?.loginButton?.setOnClickListener {
            val email = binding?.inputEmail?.text.toString().trim()
            val password = binding?.inputPassword?.text.toString().trim()

            if (binding?.inputEmail?.text?.isEmpty() == false && binding?.inputPassword?.text?.isEmpty() == false) {
                login(email, password)
            }

            else {
                Toast.makeText(requireContext(), "이메일과 비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
            }

        }

        return binding?.root
    }

    //로그인 함수
    private fun login(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 로그인 성공시 실행
                if (Authentication.checkLogin()) {
                    // 이메일 인증이 된 경우
                    Authentication.email = email
                    //db.collection("Users").document(email).get().addOnSuccessListener { documentSnapshot ->
                        //val name: String = documentSnapshot.get("Name") as String // 로그인 시 서버에서 이름 가져오기
                        // 현재 로그인된 사용자의 UID 가져오기
                        val currentUser = FirebaseAuth.getInstance().currentUser
                        val uid = currentUser?.uid
                        uid?.let { viewModel.fetchUser(it) } // 뷰모델에 개인정보 저장
                    //}
                    Toast.makeText(requireContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_loginFragment2_to_mainActivity)
                }
                // 이메일 인증이 안 된 경우
                else Toast.makeText(requireContext(), "이메일 인증에 실패했습니다.", Toast.LENGTH_SHORT).show()
            } else {
                // 로그인 실패시 실행
                Toast.makeText(requireContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}