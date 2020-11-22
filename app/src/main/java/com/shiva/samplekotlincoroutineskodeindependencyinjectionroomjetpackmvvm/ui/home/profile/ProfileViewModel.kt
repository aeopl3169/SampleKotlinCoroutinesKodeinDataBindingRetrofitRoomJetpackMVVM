package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.profile

import androidx.lifecycle.ViewModel
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.UserRepository

class ProfileViewModel(
    repository: UserRepository
) : ViewModel() {
    // Getting current user via liveData. LiveData can be binded to xml if required.
    val user = repository.getUser()
}
