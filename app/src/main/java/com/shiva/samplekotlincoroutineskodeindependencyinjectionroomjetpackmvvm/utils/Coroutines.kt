package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

// object keyword is similar to static in java
object Coroutines {

    // Unit in Kotlin is equal to void in Java
    fun main(work: suspend (() -> Unit)) =
        // "Dispatchers.Main" will run on main thread
        CoroutineScope(Dispatchers.Main).launch {
            // launch will return a job.
            work()
        }

    fun io(work: suspend (() -> Unit)) =
        CoroutineScope(Dispatchers.IO).launch {
        work()
    }

}