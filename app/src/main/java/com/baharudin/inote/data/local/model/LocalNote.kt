package com.baharudin.inote.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
import java.util.*

@Entity
data class LocalNote(
    var noteTitle : String? = null,
    var description : String? = null,
    var date : Long = System.currentTimeMillis(),
    var conected : Boolean = false,
    var locallyDelleted : Boolean = false,
    @PrimaryKey(autoGenerate = false)
    var noteId : String = UUID.randomUUID().toString()
) :Serializable
