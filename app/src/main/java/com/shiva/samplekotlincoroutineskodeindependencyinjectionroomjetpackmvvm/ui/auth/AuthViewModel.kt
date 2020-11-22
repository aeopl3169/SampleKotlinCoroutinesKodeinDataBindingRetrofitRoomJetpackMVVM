package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.auth

import androidx.lifecycle.ViewModel
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.UserRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

//class AuthViewModel : ViewModel() {
class AuthViewModel(
    private val repository: UserRepository
) : ViewModel() {

    // Function to observe/get the user, if exist in local DB
    fun getLoggedInUser() = repository.getUser()

    suspend fun userLogin(
        email: String,
        password: String
    ) = withContext(Dispatchers.IO) { repository.userLogin(email, password) }

    suspend fun saveLoggedInUser(user: User) = repository.saveUser(user)

    suspend fun userSignup(
        name: String,
        email: String,
        password: String
    ) = repository.userSignup(name, email, password)
}