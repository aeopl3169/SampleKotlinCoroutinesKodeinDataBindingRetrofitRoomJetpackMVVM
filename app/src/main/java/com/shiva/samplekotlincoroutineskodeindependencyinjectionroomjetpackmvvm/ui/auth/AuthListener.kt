package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.auth

import androidx.lifecycle.LiveData
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User

// Here AuthListener interface is to get call back (communicate) from AuthViewModel to LoginActivity.
interface AuthListener {
    fun onStarted()
//    fun onSuccess(loginResponse: LiveData<String>)
    fun onSuccess(user: User)
    fun onFailure(error: String)
}