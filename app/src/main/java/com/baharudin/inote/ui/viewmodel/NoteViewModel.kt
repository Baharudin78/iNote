package com.baharudin.inote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.inote.data.local.model.LocalNote
import com.baharudin.inote.repository.NoteRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    val noteRepo: NoteRepo
): ViewModel() {

    var oldNote : LocalNote? = null

    fun createNote(
        noteTitle : String?,
        description : String?
    ) = viewModelScope.launch(Dispatchers.IO) {
        val localNote = LocalNote(
            noteTitle = noteTitle,
            description = description
        )
        noteRepo.createNote(localNote)
    }
    fun updateNote(
        noteTitle: String?,
        description: String?
    ) = viewModelScope.launch(Dispatchers.IO) {
        if (noteTitle == oldNote?.noteTitle && description == oldNote?.description && oldNote?.conected == true) {
            return@launch
        }
        val note = LocalNote(
            noteTitle = noteTitle,
            description = description,
            noteId = oldNote!!.noteId
        )
        noteRepo.updateNote(note)
    }

    fun miliToDate(time : Long) : String {
        val date = Date(time)
        val simpleDate = SimpleDateFormat("hh:mm a | MMM d, yyyy", Locale.getDefault())
        return simpleDate.format(date)
    }
}