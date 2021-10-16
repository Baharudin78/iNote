package com.baharudin.inote.repository

import com.baharudin.inote.data.local.dao.NoteDao
import com.baharudin.inote.data.local.model.LocalNote
import com.baharudin.inote.data.remote.api.NoteApi
import com.baharudin.inote.data.remote.model.RemoteNote
import com.baharudin.inote.data.remote.model.User
import com.baharudin.inote.utils.Result
import com.baharudin.inote.utils.SessionManager
import com.baharudin.inote.utils.isNetworkConnected
import javax.inject.Inject

class NoteRepoImplementation @Inject constructor(
    val noteApi : NoteApi,
    val noteDao : NoteDao,
    val sessionManager: SessionManager
) : NoteRepo {
    override suspend fun createUser(user: User): Result<String> {
        return try {
            if(!isNetworkConnected(sessionManager.context)){
                Result.Error<String>("No Internet Connection!")
            }

            val result = noteApi.createAccount(user)
            if(result.succes){
                sessionManager.updateSession(result.messege,user.username ?:"",user.email)
                Result.Succes("User Created Successfully!")
            } else {
                Result.Error(result.messege)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun login(user: User): Result<String> {
        return try {
            if(!isNetworkConnected(sessionManager.context)){
                Result.Error<String>("No Internet Connection!")
            }

            val result = noteApi.loginAccount(user)
            if(result.succes){
                sessionManager.updateSession(result.messege,user.username ?:"",user.email)
                Result.Succes("Logged In Successfully!")
            } else {
                Result.Error(result.messege)
            }
        }catch (e:Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun getUser(): Result<User> {
        return try {
            val name = sessionManager.getCurrentUserName()
            val email = sessionManager.getCurrentEmail()
            if(name == null || email == null){
                Result.Error<User>("User not Logged In!")
            }
            Result.Succes(name?.let { User(it,email!!,"") })
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun logOut(): Result<String> {
        return try {
            sessionManager.logOut()
            Result.Succes("Logged Out Successfully!")
        } catch (e:Exception){
            e.printStackTrace()
            Result.Error(e.message ?: "Some Problem Occurred!")
        }
    }

    override suspend fun createNote(note: LocalNote): Result<String> {
        return try {
            noteDao.insertNote(note)
            val token = sessionManager.getJwtToken()
            if (token == null) {
                Result.Succes("Succesfully insert data")
            }
            val result = noteApi.createNote(
                "Bearer $token",
                RemoteNote(
                    noteTitle = note.noteTitle,
                    description = note.description,
                    date = note.date,
                    id = note.noteId
                )
            )
            if (result.succes) {
                noteDao.insertNote(note.also { it.conected = true })
                Result.Succes("Note saved succesfully")
            }else {
                Result.Error(result.messege)
            }
        }catch (e : Exception) {
            e.printStackTrace()
            Result.Error(e.message ?: "SOme Problem Occured")
        }
    }

    override suspend fun updateNote(note: LocalNote): Result<String> {
        return try {
            noteDao.insertNote(note)
            val token = sessionManager.getJwtToken()
            if (token == null) {
                Result.Succes(" Note Updated in local database")
            }
            val result = noteApi.updateNote(
                "Bearer $token",
                RemoteNote(
                    noteTitle = note.noteTitle,
                    description = note.description,
                    date = note.date,
                    id = note.noteId
                )
            )
            if (result.succes) {
                noteDao.insertNote(note.also { it.conected = true })
                Result.Succes("Note Successfully updated")
            }else {
                Result.Error(result.messege)
            }
        }catch (e : Exception) {
            e.printStackTrace()
            Result.Error(e.message ?:"SOme problem occured")
        }
    }
}