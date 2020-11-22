package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.QuotesRepository

// We can not pass parameters directly from QuotesViewModel, so we crete and pass through ViewModelFactory(QuotesViewModelFactory)
// QuotesViewModelFactory will return QuotesViewModel with the required parameter.
@Suppress("UNCHECKED_CAST")
class QuotesViewModelFactory(
    private val repository: QuotesRepository
) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        // creating the QuotesViewModel instance and returning as T.
        return QuotesViewModel(repository) as T
    }
}