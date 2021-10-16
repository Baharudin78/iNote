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
import com.baharudin.inote.data.remote.model.User
import com.baharudin.inote.databinding.FragmentLoginBinding
import com.baharudin.inote.ui.viewmodel.UserViewModel
import com.baharudin.inote.utils.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch


@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {

    private var _binding : FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val userViewModel : UserViewModel by activityViewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentLoginBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        subcribeLogin()

        val nama = binding.namaEt.text.toString()
        val email = binding.emailEditTxt.text.toString()
        val password = binding.passwordEdtTxt.text.toString()

        userViewModel.loginUser(
            nama.trim(),
            email.trim(),
            password.trim()
        )
    }
    private fun subcribeLogin() = lifecycleScope.launch {
        userViewModel.loginState.collect{result ->
            when(result) {
                is Result.Succes -> {
                    hideProgressBar()
                    Toast.makeText(requireContext(), "Login SUccesfully", Toast.LENGTH_SHORT).show()
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
        binding.loginProgressBar.isVisible = true
    }

    private fun hideProgressBar(){
        binding.loginProgressBar.isVisible = false
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}