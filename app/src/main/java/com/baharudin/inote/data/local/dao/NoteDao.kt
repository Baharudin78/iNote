package com.baharudin.inote.data.local.dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.baharudin.inote.data.local.model.LocalNote
import kotlinx.coroutines.flow.Flow

interface NoteDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(note : LocalNote)

    @Query("SELECT * FROM LocalNote WHERE locallyDelleted = 0 ORDER BY date DESC")
    fun getAllNoteByDate() : Flow<List<LocalNote>>

    @Query("DELETE FROM LocalNote WHERE noteId =:noteId")
    suspend fun deleteNote(noteId : String)

    @Query("UPDATE LocalNote SET locallyDelleted = 1 WHERE noteId =:noteId")
    suspend fun deleteNoteLocally(noteId : String)

    @Query("SELECT * FROM LocalNote WHERE conected = 0")
    suspend fun getAllLocalNote(): List<LocalNote>

    @Query("SELECT * FROM LocalNote WHERE locallyDelleted = 1")
    suspend fun getAllLocallyNotes() : List<LocalNote>
}