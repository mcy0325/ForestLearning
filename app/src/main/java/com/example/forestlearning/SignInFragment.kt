package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentSignInBinding
import com.example.forestlearning.Authentication.Companion.auth
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class SignInFragment : Fragment() {

    private var binding: FragmentSignInBinding? = null
    lateinit var mAuth : FirebaseAuth
    private lateinit var mDbRef : DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(inflater, container, false)

        mAuth = Firebase.auth

        mDbRef = Firebase.database.reference

        binding?.signInEndButton?.setOnClickListener {
            val name = binding?.editName?.text.toString().trim()
            val email = binding?.editEmail?.text.toString().trim()
            val password = binding?.editPassword?.text.toString().trim()
            signIn(name, email, password)
        }

        return binding?.root
    }

    private fun signIn(name: String, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)?.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener { sendTask ->
                    if (sendTask.isSuccessful) {
                        //회원가입 성공시 실행
                        Toast.makeText(requireContext(), "회원가입에 성공했습니다. 이메일 인증 후 로그인이 가능합니다.", Toast.LENGTH_SHORT).show()
                        addUser(name, email, mAuth.uid.toString())
                        findNavController().navigate(R.id.action_signInFragment2_to_loginFragment2)
                    } else {
                        Toast.makeText(requireContext(), "이메일 전송에 실패했습니다.", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(R.id.action_signInFragment2_to_loginFragment2)
                    }
                }
            } else {
                Toast.makeText(requireContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
            }
        }?.addOnFailureListener {
            Toast.makeText(requireContext(), "회원가입에 실패했습니다.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun addUser(name: String, email: String, uId: String) {
        mDbRef.child("Users").child(uId).setValue(UserData(name, email, uId))
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}