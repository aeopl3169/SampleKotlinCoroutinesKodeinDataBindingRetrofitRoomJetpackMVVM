package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.UserRepository

// We can not pass parameters directly from AuthViewModel, so we crete and pass through ViewModelFactory(AuthViewModelFactory)
// AuthViewModelFactory will return AuthViewModel with the required parameter.
@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // creating the AuthViewModel instance and returning as T.
        return AuthViewModel(repository) as T
    }
}