package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentLogoutBinding
import com.example.forestlearning.viewmodel.UserViewModel

class LogoutFragment : Fragment() {

    private var binding: FragmentLogoutBinding? = null
    val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // FragmentLogoutBinding을 인플레이트하여 binding 객체를 생성
        binding = FragmentLogoutBinding.inflate(inflater, container, false)

        // 현재 로그인한 사용자의 ID를 가져오기
        val uid = Authentication.auth.currentUser?.uid
        // uid가 null이 아니라면, 해당 uid의 사용자 정보를 가져오기
        if (uid != null) {
            viewModel.fetchUser(uid)
        }

        // 사용자 이름이 변경될 때마다 텍스트 뷰의 내용을 업데이트
        viewModel.name.observe(viewLifecycleOwner){ newName ->
            binding?.txtName?.text = "$newName 님"
        }

        // 로그아웃 버튼을 클릭하면 로그아웃 처리를 하고, 로그인 화면으로 이동
        binding?.logoutButton?.setOnClickListener {
            Authentication.auth.signOut()
            Authentication.email = null
            findNavController().navigate(R.id.action_logoutFragment_to_hostActivity)
        }

        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}