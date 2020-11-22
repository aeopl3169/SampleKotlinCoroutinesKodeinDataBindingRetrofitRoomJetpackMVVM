package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.CoroutineStart


//**
// Custom lazy block
// fun(lazyDeferred) of generic type 'T' which will take a block of suspend function that should be executing
// in the CoroutineScope and return type is generic type 'T'.
// "lazyDeferred" function will return "Lazy" of type "Deferred" of type<T> (Lazy<Deferred<T>>).
// Deferred is a job with result. */
fun <T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>> {
    return lazy {
        // By defining "GlobalScope.async as start = CoroutineStart.LAZY" everything will be lazy
        GlobalScope.async(start = CoroutineStart.LAZY) {
            // invoking the block
            block.invoke(this)
        }
    }
}