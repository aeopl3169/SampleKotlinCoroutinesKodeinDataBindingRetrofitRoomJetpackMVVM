package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network

import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.responses.AuthResponse
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.responses.QuotesResponse
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST

// This interface is for Retrofit API call. Repository will communicate with this MyApi.
interface MyApi {

    // @FormUrlEncoded use this annotation when endpoint url is of type post(i.e. @POST annotation)
    @FormUrlEncoded
    @POST("login")
    // Suspend functions are center for coroutines. Suspend functions can be paused and resumed when required.
    // Suspend functions will run Long running operations without blocking till the end. Usually network calls are long running operations.
    // Just declare "suspend" keyword before the function.
    suspend fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
//    ): Call<ResponseBody>
    ): Response<AuthResponse>

    @FormUrlEncoded
    @POST("signup")
    suspend fun userSignup(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
        ): Response<AuthResponse>

    //https://api.simplifiedcoding.in/course-apis/mvvm/quotes
    @GET("quotes")
    suspend fun getQuotes(): Response<QuotesResponse>

    companion object {
        // invoke will be called automatically, when we call the MyApi interface "MyApi()".
        operator fun invoke(
            networkInterceptorOkHttp: NetworkInterceptorOkHttp
        ): MyApi {
            // To connect Interceptor(NetworkInterceptorOkHttp) to Retrofit we need okHttpClient
            val logging = HttpLoggingInterceptor()
            val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(networkInterceptorOkHttp) // Adding interceptor through "addInterceptor" method.
                .addInterceptor(ChuckerInterceptor(networkInterceptorOkHttp.applicationContext)) // Chucker logging, we can see in notification.
                .addInterceptor(logging) // Logger Interceptor
                .addNetworkInterceptor(StethoInterceptor())
                .build()
            return Retrofit.Builder()
                .client(okHttpClient) // Adding okHttpClient Client to Retrofit.
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build() // Building retrofit instance
                .create(MyApi::class.java) // creating our interface from retrofit instance.
        }
    }
}