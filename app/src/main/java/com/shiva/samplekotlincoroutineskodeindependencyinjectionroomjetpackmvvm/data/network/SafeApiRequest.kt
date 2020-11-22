package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network

import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.ApiException
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Response
import java.lang.StringBuilder

// Handling all the errors
abstract class SafeApiRequest {
    // Generic function to perform API request and response
    /** generic function of type "Any". The function "apiRequest" performs API request and returns T.
     * "apiRequest" takes the api call. The api call is another suspend function with no parameter and response of type T */
    suspend fun <T : Any> apiRequest(call: suspend () -> Response<T>): T {
        val response = call.invoke() // Invoke the call and get the response
        if (response.isSuccessful) {
            return response.body()!!
        } else {
            val err = response.errorBody()?.string()
            val message = StringBuilder()
            // Checking "err" is not null
            err?.let {
                try {
                    // checking Json error response message and appending.
                    message.append(JSONObject(it).getString("message"))
                } catch (e: JSONException) {
                }
                message.append("\n")
            }
            message.append("Error code: ${response.code()}")
            throw ApiException(message.toString())
        }
    }
}