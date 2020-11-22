package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.NoInternetException
import okhttp3.Interceptor
import okhttp3.Response

/**
 * NetworkInterceptorOkHttp class is to check network is available or not
 *
 * @constructor For checking network connections context is required, so we passed in constructor.
 *
 * @param context
 */
class NetworkInterceptorOkHttp(
    context: Context
) : Interceptor {

    val applicationContext = context.applicationContext
    /**
     * This override function will intercept the network calls.
     *
     * @param chain
     * @return
     */
    override fun intercept(chain: Interceptor.Chain): Response {
        if (!isInterceptorAvailable())
            throw NoInternetException(" Check the internet connection.")
        return chain.proceed(chain.request())
    }

//    @RequiresApi(Build.VERSION_CODES.M)
    private fun isInterceptorAvailable(): Boolean {
        var result = false
        // In kotlin we use "as" keyword to cast.
        // ConnectivityManager tells network status i.e. internet available or not.
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
/*
        connectivityManager?.let {
            it.getNetworkCapabilities(connectivityManager.activeNetwork).apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
*/
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val nw = connectivityManager?.activeNetwork ?: return false
            val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
            return when {
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                //for other device how are able to connect with Ethernet
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
                //for check internet over Bluetooth
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
                else -> false
            }
        } else {
            connectivityManager.run {
                @Suppress("DEPRECATION")
                connectivityManager?.activeNetworkInfo?.run {
                    result = when (type) {
                        ConnectivityManager.TYPE_WIFI -> true
                        ConnectivityManager.TYPE_MOBILE -> true
                        ConnectivityManager.TYPE_ETHERNET -> true
                        ConnectivityManager.TYPE_BLUETOOTH -> true
                        else -> false
                    }
                }
            }
            return result
//            val nwInfo = connectivityManager?.activeNetworkInfo ?: return false
//            return nwInfo.isConnected
        }
    }
}