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
    private var binding : FragmentLogoutBinding? = null
    val viewModel: UserViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLogoutBinding.inflate(inflater, container, false)
        binding?.logoutName?.text = viewModel.name.value

        binding?.logoutYesButton?.setOnClickListener {
            Authentication.auth.signOut()
            Authentication.email = null
            findNavController().navigate(R.id.action_logoutFragment2_to_loginFragment2)
        }

        binding?.logoutNoButton?.setOnClickListener {
            findNavController().navigate(R.id.action_logoutFragment2_to_mainActivity)
        }

        return inflater.inflate(R.layout.fragment_logout, container, false)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}