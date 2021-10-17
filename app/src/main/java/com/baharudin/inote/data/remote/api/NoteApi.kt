package com.baharudin.inote.data.remote.api

import com.baharudin.inote.data.remote.model.RemoteNote
import com.baharudin.inote.data.remote.model.SimpleResponse
import com.baharudin.inote.data.remote.model.User
import com.baharudin.inote.utils.Constant.API_VERSION
import retrofit2.http.*

interface NoteApi {

    @Headers("Content-Type: application/json")
    @POST("$API_VERSION/users/register")
    suspend fun createAccount(
        @Body user: User
    ) : SimpleResponse

    @Headers("Content-Type: Application/json")
    @POST("$API_VERSION/users/login")
    suspend fun loginAccount(
        @Body user: User
    ): SimpleResponse

    @Headers("Content-Type: Application/json")
    @POST("$API_VERSION/notes/create")
    suspend fun createNote(
        @Header("Authorization") token : String,
        @Body remoteNote: RemoteNote
    ) : SimpleResponse

    @Headers("Content-Type: Application/json")
    @GET("$API_VERSION/notes")
    suspend fun getAllNote(
        @Header("Authorization") token: String
    ) : List<RemoteNote>

    @Headers("Content-Type: Application/json")
    @POST("$API_VERSION/notes/update")
    suspend fun updateNote(
        @Header("Authorization") token: String,
        @Body remoteNote: RemoteNote
    ) : SimpleResponse

    @Headers("Content-Type: Application/json")
    @DELETE("$API_VERSION/notes/delete")
    suspend fun deleteNote(
        @Header("Authorization") token: String,
        @Query("id") noteId:String
    ) : SimpleResponse


}