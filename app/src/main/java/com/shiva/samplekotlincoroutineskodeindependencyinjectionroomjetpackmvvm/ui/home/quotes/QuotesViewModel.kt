package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.ui.home.quotes

import androidx.lifecycle.ViewModel
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories.QuotesRepository
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.lazyDeferred

// Since repository (QuotesRepository) is passes as argument in QuotesViewModel constructor
// we need QuotesViewModelFactory to call the QuotesViewModel in QuotesFragment.
class QuotesViewModel(
    repository: QuotesRepository
) : ViewModel() {
    /** Since,
     * a) we can not call suspend function(getQuotes) directly,
     * b) We don't want to initialize Quotes where ever the QuotesViewModel in instantiated/created,
     * we should get the Quotes when it is needed, so we write custom lazy block(lazyDeferred in Delegates.kt)
     * which will use CoroutineScope to make the call. */
    val quotes by lazyDeferred {
        repository.getQuotes()
    }
}
