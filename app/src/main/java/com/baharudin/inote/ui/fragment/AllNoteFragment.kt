package com.baharudin.inote.ui.fragment

import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.baharudin.inote.R
import com.baharudin.inote.adapter.NoteAdapter
import com.baharudin.inote.databinding.FragmentAllNoteBinding
import com.baharudin.inote.ui.viewmodel.NoteViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AllNoteFragment : Fragment(R.layout.fragment_all_note) {

    private var _binding : FragmentAllNoteBinding ? = null
    private val binding get() = _binding!!
    private val noteViewModel : NoteViewModel by activityViewModels()
    private lateinit var noteAdapter : NoteAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding = FragmentAllNoteBinding.bind(view)
        super.onViewCreated(view, savedInstanceState)
        (activity as AppCompatActivity).setSupportActionBar(binding.customToolBar)

        binding.newNoteFab.setOnClickListener {
            findNavController().navigate(R.id.action_allNoteFragment_to_newNoteFragment)
        }
        setupRecycleview()
        subcribeToken()

    }
    private fun setupRecycleview() {
        noteAdapter = NoteAdapter()
        noteAdapter.setOnItemClickListener {
            val action = AllNoteFragmentDirections.actionAllNoteFragmentToNewNoteFragment(it)
            findNavController().navigate(action)
        }
        binding.noteRecyclerView.apply {
            adapter = noteAdapter
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            ItemTouchHelper(itemTouchHelper)
                .attachToRecyclerView(this)
        }
    }
    private fun subcribeToken() = lifecycleScope.launch {
        noteViewModel.notes.collect {
            noteAdapter.notes = it
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
    private val itemTouchHelper = object : ItemTouchHelper.SimpleCallback(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return true
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.layoutPosition
            val note = noteAdapter.notes[position]
            Snackbar.make(
                requireView(),
                "Note Deleted succesfully",
                Snackbar.LENGTH_LONG
            ).apply {
                setAction(
                    "Undo"
                ) {
                    noteViewModel.undoDelete(note)
                }
                show()
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {
            super.onChildDraw(c, recyclerView, viewHolder, dX/2, dY, actionState, isCurrentlyActive)
        }

    }

}
