package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentCreateAccountBinding
import com.baharudin.inote.ui.viewmodel.UserViewModel
import com.baharudin.inote.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {

    private var _binding : FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    private val userViewModel : UserViewModel by activityViewModels()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreateAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        subcribeToRegister()
        binding.createAccountBtn.setOnClickListener {
            val nama = binding.userNameEdtTxt.text.toString()
            val email = binding.emailEditTxt.text.toString()
            val password = binding.passwordEdtTxt.text.toString()
            val confirmPassword = binding.passwordReEnterEdtTxt.text.toString()

            userViewModel.createUser(
                nama.trim(),
                email.trim(),
                password.trim(),
                confirmPassword.trim()
            )
        }
    }
    private fun subcribeToRegister()  = lifecycleScope.launch {
        userViewModel.reggisterState.collect{result ->
            when(result) {
                is Result.Succes -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Account Has been created", Toast.LENGTH_SHORT).show()
                    findNavController().popBackStack()
                }
                is Result.Error -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), result.messege, Toast.LENGTH_SHORT).show()
                }
                is Result.Loading -> {
                    showProgressBar()
                }
            }
        }
    }
    private fun showProgressBar(){
        binding.createUserProgressBar.isVisible = true
    }

    private fun hideProgressBar(){
        binding.createUserProgressBar.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}