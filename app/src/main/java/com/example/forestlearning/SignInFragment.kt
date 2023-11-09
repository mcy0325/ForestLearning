package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentSignInBinding
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.database

class SignInFragment : Fragment() {

    private var binding: FragmentSignInBinding? = null
    private var mAuth: FirebaseAuth? = null
    private lateinit var mDbRef: DatabaseReference
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater)

        //인증 초기화
        mAuth = Firebase.auth

        //db 초기화
        mDbRef = Firebase.database.reference

        binding!!.signInEndButton.setOnClickListener {
            val name = binding!!.editName.text.toString().trim()
            val email = binding!!.editEmail.text.toString().trim()
            val password = binding!!.editPassword.text.toString().trim()
            signIn(name, email, password)
        }

        return binding!!.root
    }

    private fun signIn(name: String, email: String, password: String) {
        mAuth?.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // 회원가입 성공시 실행
                Toast.makeText(requireContext(), "회원가입에 성공했습니다.", Toast.LENGTH_SHORT).show()
                // 회원가입 완료 버튼 누르면 로그인 화면으로 이동
                binding?.signInEndButton?.setOnClickListener {
                    findNavController().navigate(R.id.action_loginFragment_to_signInFragment)
                }
                addUserToDatabase(name, email, mAuth!!.currentUser?.uid!!)

            } else {
                // 회원가입 실패시 실행
                Toast.makeText(requireContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun addUserToDatabase(name: String, email: String, uId: String) {
        mDbRef.child("user").child(uId).setValue(UserData(name, email, uId))
    }
}