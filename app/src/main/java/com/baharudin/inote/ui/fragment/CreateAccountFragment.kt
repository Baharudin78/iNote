package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentCreateAccountBinding

class CreateAccountFragment : Fragment(R.layout.fragment_create_account) {

    private var _binding : FragmentCreateAccountBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentCreateAccountBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
    }
}