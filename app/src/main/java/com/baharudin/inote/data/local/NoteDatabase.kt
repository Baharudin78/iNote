package com.baharudin.inote.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.baharudin.inote.data.local.dao.NoteDao
import com.baharudin.inote.data.local.model.LocalNote

@Database(
    entities = [LocalNote::class],
    version = 1,
    exportSchema = false
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun getNoteDao() : NoteDao
}