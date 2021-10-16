package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentAllNoteBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AllNoteFragment : Fragment(R.layout.fragment_all_note) {

    private var _binding : FragmentAllNoteBinding ? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAllNoteBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)

        binding.newNoteFab.setOnClickListener {
            findNavController().navigate(R.id.action_allNoteFragment_to_newNoteFragment)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true)
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.account -> {
                findNavController().navigate(R.id.action_allNoteFragment_to_userInfoFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }

}
