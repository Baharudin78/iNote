package com.baharudin.inote.ui.fragment

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import com.baharudin.inote.R
import com.baharudin.inote.databinding.FragmentNewNoteBinding
import com.baharudin.inote.ui.viewmodel.NoteViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class NewNoteFragment : Fragment(R.layout.fragment_new_note) {

    private var _binding : FragmentNewNoteBinding? = null
    private val binding get() = _binding!!

    val noteViewModel : NoteViewModel by activityViewModels()
    val args : NewNoteFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentNewNoteBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)

        noteViewModel.oldNote = args.localNote
        noteViewModel.oldNote?.noteTitle?.let {
            binding.newNoteTitleEditText.setText(it)
        }
        noteViewModel.oldNote?.description.let {
            binding.newNoteDescriptionEditText.setText(it)
        }
        binding.date.isVisible = noteViewModel.oldNote != null
        noteViewModel.oldNote?.date?.let {
            binding.date.text = noteViewModel.miliToDate(it)
        }
    }

    override fun onPause() {
        super.onPause()
        if(noteViewModel.oldNote == null){
            createNote()
        } else {
            updateNote()
        }
    }

    private fun updateNote() {
        val noteTitle = binding.newNoteTitleEditText.text.toString().trim()
        val noteDescription = binding.newNoteDescriptionEditText.text.toString().trim()

        if (noteTitle.isNullOrEmpty() && noteDescription.isNullOrEmpty()) {
            return
        }
        noteViewModel.updateNote(noteTitle, noteDescription)
    }

    private fun createNote() {
        val noteTitle = binding.newNoteTitleEditText.text.toString().trim()
        val noteDescribtion = binding.newNoteDescriptionEditText.text.toString().trim()

        if (noteTitle.isNullOrEmpty() && noteDescribtion.isNullOrEmpty()) {
            Toast.makeText(requireContext(), "Note is empty", Toast.LENGTH_SHORT).show()
            return
        }
        noteViewModel.createNote(noteTitle, noteDescribtion)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}