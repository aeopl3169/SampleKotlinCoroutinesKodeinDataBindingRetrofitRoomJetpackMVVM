package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.AppDatabase
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.MyApi
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.network.SafeApiRequest
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.preferences.PreferenceProvider
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.utils.Coroutines
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDateTime
import org.threeten.bp.temporal.ChronoUnit

//import java.time.LocalDateTime
//import java.time.temporal.ChronoUnit

private val MINIMUM_INTERVAL = 6

class QuotesRepository(
    private val api: MyApi,
    private val db: AppDatabase,
    private val prefs: PreferenceProvider
) : SafeApiRequest() {
    private val quotes = MutableLiveData<List<Quote>>()

    init {
        // "observeForever" will be called when there is change in quotes(MyApi).
        // Here we are observing LiveData changes from MyApi and fetching new values into local DB and the old data is overridden.
        quotes.observeForever {
            // Push the changes to local DB
            saveQuotes(it)
        }
    }

    // Fetching quotes from backend API
    private suspend fun fetchQuotes() {
        // Getting last save time stamp.
        val lastSavedAt = prefs.getLastSavedAt()

        if (lastSavedAt == null || isFetchNeeded(LocalDateTime.parse(lastSavedAt))) {
            // To use the "apiRequest" suspend function we inherit the SafeApiRequest in the QuotesRepository class.
            val response = apiRequest {
                api.getQuotes() // Performing the Api request.
            }
            // Putting the response(quotes) to LiveData
            quotes.postValue(response.quotes)
        }
    }

    // function to call from ViewModel
    suspend fun getQuotes(): LiveData<List<Quote>> {
        // For CoroutineScope we use withContext
        return withContext(Dispatchers.IO) {
            fetchQuotes()
            // Return all the quotes from local database.
            db.getQuoteDao().getQuotes()
        }
    }

    /**
     * Checking where to fetch from DB or from server. true from server false from SharedPreferences.
     *
     * @param savedAt getting last saved time.
     * @return true if more than 6 hours else false
     */
    private fun isFetchNeeded(savedAt: LocalDateTime): Boolean {
        return ChronoUnit.HOURS.between(savedAt, LocalDateTime.now()) > MINIMUM_INTERVAL
    }

    // Fetching quotes from DB.
    private fun saveQuotes(quotes: List<Quote>) {
        Coroutines.io {
            prefs.saveLastSavedAt(LocalDateTime.now().toString())
            db.getQuoteDao().saveAllQuotes(quotes)
        }
    }
}