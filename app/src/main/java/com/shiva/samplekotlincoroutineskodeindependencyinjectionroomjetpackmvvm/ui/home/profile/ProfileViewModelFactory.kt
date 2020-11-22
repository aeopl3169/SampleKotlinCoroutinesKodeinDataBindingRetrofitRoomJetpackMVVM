package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.UserRepository

// We can not pass parameters directly from AuthViewModel, so we crete and pass through ViewModelFactory(ProfileViewModelFactory)
// ProfileViewModelFactory will return ProfileViewModel with the required parameter.
@Suppress("UNCHECKED_CAST")
class ProfileViewModelFactory(
    private val repository: UserRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // creating the AuthViewModel instance and returning as T.
        return ProfileViewModel(repository) as T
    }
}