package com.baharudin.inote.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.baharudin.inote.data.remote.model.User
import com.baharudin.inote.repository.NoteRepo
import com.baharudin.inote.utils.Constant.MINIMUM_PASSWORD_LENGTH
import dagger.hilt.android.HiltAndroidApp
import com.baharudin.inote.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import java.util.regex.Pattern
import javax.inject.Inject

@HiltViewModel
class UserViewModel @Inject constructor(
    val noteRepo : NoteRepo
) : ViewModel(){

    private var _register = MutableSharedFlow<Result<String>>()
    val reggisterState : SharedFlow<Result<String>> = _register

    private var _login = MutableSharedFlow<Result<String>>()
    val loginState : SharedFlow<Result<String>> = _login

    private var _currentUser = MutableSharedFlow<Result<User>>()
    val currentUser : SharedFlow<Result<User>> = _currentUser

    fun createUser(
        nama : String,
        email : String,
        password : String,
        confirmPassword : String
    ) = viewModelScope.launch {
        _register.emit(Result.Loading())

        if (nama.isEmpty() || email.isEmpty() || confirmPassword.isEmpty() || password != confirmPassword) {
            _register.emit(Result.Error("Some field is empty"))
            return@launch
        }
        if(!isEmailisValidate(email)) {
            _register.emit(Result.Error("Email is not valid"))
            return@launch
        }
        if (!isPasswordValid(password)) {
            _register.emit(Result.Error("Password is not valid"))
            return@launch
        }
        val newUser = User(
            nama,
            email,
            password
        )
        _register.emit(noteRepo.createUser(newUser))
    }
    fun loginUser(
        nama: String,
        email: String,
        password: String
    ) = viewModelScope.launch {
        _login.emit(Result.Loading())

        if (email.isEmpty() || password.isEmpty()) {
            _login.emit(Result.Error("Some Field is empty"))
            return@launch
        }
        if (!isEmailisValidate(email)) {
            _login.emit(Result.Error("Email is not valid"))
            return@launch
        }
        if (!isPasswordValid(password)) {
            _login.emit(Result.Error("Password should be between $MINIMUM_PASSWORD_LENGTH character"))
            return@launch
        }
        val newLogin = User(
            nama,
            email,
            password
        )
        _login.emit(noteRepo.login(newLogin))
    }
    fun getCUrrentUser() = viewModelScope.launch {
        _currentUser.emit(Result.Loading())
        _currentUser.emit(noteRepo.getUser())
    }
    fun logOutUser() = viewModelScope.launch {
        val result = noteRepo.logOut()
        if (result is Result.Succes) {
            getCUrrentUser()
        }
    }
    private fun isEmailisValidate(email: String) : Boolean {
        val regex = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$"
        val pattern = Pattern.compile(regex)
        return (email.isNotEmpty() && pattern.matcher(email).matches())
    }
    private fun isPasswordValid(password: String) : Boolean {
        return (password.length >= MINIMUM_PASSWORD_LENGTH)
    }
}