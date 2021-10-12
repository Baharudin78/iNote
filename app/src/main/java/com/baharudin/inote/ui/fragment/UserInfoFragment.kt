package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentUserInfoBinding

class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private var _binding : FragmentUserInfoBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentUserInfoBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            createAccountBtn.setOnClickListener {
                findNavController().navigate(R.id.action_userInfoFragment_to_createAccountFragment)
            }
            loginBtn.setOnClickListener {
                findNavController().navigate(R.id.action_userInfoFragment_to_loginFragment)
            }
        }
    }
}