package com.baharudin.inote.repository

import com.baharudin.inote.data.local.model.LocalNote
import com.baharudin.inote.data.remote.model.User
import com.baharudin.inote.utils.Result

interface NoteRepo {

    suspend fun createUser(user: User) : Result<String>
    suspend fun login(user: User) : Result<String>
    suspend fun getUser() : Result<User>
    suspend fun logOut() : Result<String>

    suspend fun createNote(note : LocalNote) : Result<String>
    suspend fun updateNote(note: LocalNote) : Result<String>
}