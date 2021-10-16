package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentUserInfoBinding
import com.baharudin.inote.ui.viewmodel.UserViewModel
import com.baharudin.inote.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class UserInfoFragment : Fragment(R.layout.fragment_user_info) {

    private var _binding : FragmentUserInfoBinding? = null
    private val binding get() = _binding!!
    private val userViewModel : UserViewModel by activityViewModels()

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
            logoutBtn.setOnClickListener {
                userViewModel.logOutUser()
            }

        }
        subscribeToCurrentUserEvents()
    }

    private fun subscribeToCurrentUserEvents() = lifecycleScope.launch {
        userViewModel.currentUser.collect{result ->
            when(result) {
                is Result.Succes -> {
                    userLoggedIn()
                    binding.userTxt.text = result.data?.username ?: "NO Name"
                    binding.userEmail.text = result.data?.email ?: "No Email"
                }
                is Result.Error -> {
                    userNotLoggedIn()
                    binding.userTxt.text = "Not Logged In!"
                }
                is Result.Loading -> {
                    binding.userProgressBar.isVisible = true
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        userViewModel.getCUrrentUser()
    }
    private fun userLoggedIn(){
        binding.userProgressBar.isVisible = false
        binding.loginBtn.isVisible = false
        binding.createAccountBtn.isVisible = false
        binding.logoutBtn.isVisible = true
        binding.userEmail.isVisible = true
    }

    private fun userNotLoggedIn(){
        binding.userProgressBar.isVisible = false
        binding.loginBtn.isVisible = true
        binding.createAccountBtn.isVisible = true
        binding.logoutBtn.isVisible = false
        binding.userEmail.isVisible = false
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}