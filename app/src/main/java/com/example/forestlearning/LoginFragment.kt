package com.example.forestlearning

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentLoginBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth

class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private var mAuth: FirebaseAuth? = null
    private val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //회원가입 버튼을 누르면 회원가입 화면으로 이동
        binding?.signInButton?.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment2_to_signInFragment2)
        }

        mAuth = Firebase.auth

        //로그인 버튼 이벤트
        binding?.loginButton?.setOnClickListener {
            val email = binding?.inputEmail?.text.toString().trim()
            val password = binding?.inputPassword?.text.toString().trim()
            findNavController().navigate(R.id.action_loginFragment2_to_mainActivity)

            login(email, password)

        }

    }

    //로그인 함수
    private fun login(email: String, password: String) {
        mAuth?.signInWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // 로그인 성공시 실행
                    Toast.makeText(requireContext(), "로그인에 성공했습니다.", Toast.LENGTH_SHORT).show()

                } else {
                    // 로그인 실패시 실행
                    Toast.makeText(requireContext(), "로그인에 실패했습니다.", Toast.LENGTH_SHORT).show()
                    Log.d("로그인", "실패 원인: ${task.exception}")
                }
            }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}