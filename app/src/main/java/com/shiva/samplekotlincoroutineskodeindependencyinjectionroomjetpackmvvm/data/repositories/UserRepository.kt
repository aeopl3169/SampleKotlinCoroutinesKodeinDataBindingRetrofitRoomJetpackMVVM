package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories

import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.AppDatabase
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.MyApi
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.SafeApiRequest
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.responses.AuthResponse

//class UserRepository {
//class UserRepository : SafeApiRequest(){
class UserRepository(
    private val api: MyApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
//        return apiRequest { MyApi().userLogin(email, password) }
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(name: String, email: String, password: String): AuthResponse {
        return apiRequest { api.userSignup(name, email, password) }
    }

    // function to save the user.
    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    // function to get the user.
    fun getUser() = db.getUserDao().getUser()
/*
    suspend fun userLogin(email: String, password: String): Response<AuthResponse> {
        return MyApi().userLogin(email, password)
    }
*/

/*
    fun userLogin(email: String, password: String): LiveData<String> {

        val loginResponse = MutableLiveData<String>()

        // Calling MyApi(i.e. nutting but creating instance) directly results in tight coupling, instead we should use Dependency Injection.
        // Should not create instance of other classes inside the class.
        MyApi().userLogin(email, password)
            .enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    loginResponse.value = t.message
                }

                override fun onResponse(
                    call: Call<ResponseBody>,
                    response: Response<ResponseBody>
                ) {
                    if (response.isSuccessful) {
                        loginResponse.value = response.body()?.string()
                    } else {
                        loginResponse.value = response.errorBody()?.string()
                    }
                }

            })
        return loginResponse
    }
 */
}