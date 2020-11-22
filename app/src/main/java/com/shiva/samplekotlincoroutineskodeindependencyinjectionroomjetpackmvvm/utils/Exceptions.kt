package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils

import java.io.IOException

// All the exceptions classes
class ApiException(message: String) : IOException(message)

class NoInternetException(message: String) : IOException(message)