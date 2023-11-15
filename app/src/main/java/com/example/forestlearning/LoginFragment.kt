package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentLoginBinding
import com.example.forestlearning.Authentication.Companion.auth

class LoginFragment : Fragment() {

    private var binding: FragmentLoginBinding? = null

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