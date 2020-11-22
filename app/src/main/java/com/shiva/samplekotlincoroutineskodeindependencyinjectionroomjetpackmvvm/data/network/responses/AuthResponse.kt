package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.responses

import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User

// This data class is to store the Json response
// data class "AuthResponse" directly parses JSON response to Kotlin objects.
data class AuthResponse(
    val isSuccessful: Boolean?,
    val message: String?,
    val user: User?
)