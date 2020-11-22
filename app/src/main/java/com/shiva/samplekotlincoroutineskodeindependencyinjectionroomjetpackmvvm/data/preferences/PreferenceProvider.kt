package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

private const val KEY_SAVED_AT = "key_saved_at"

// To save last fetched time
class PreferenceProvider(
    context: Context
) {
    // Since saving in context may lead to memory leaks.
    // So, here we are saving in applicationContext from Context, passes in parameter.
    private val appContext = context.applicationContext

    private val preference: SharedPreferences
        get() = PreferenceManager.getDefaultSharedPreferences(appContext)

    /**
     * Will save the last saved time.
     *
     * @param savedAt time is passed
     */
    fun saveLastSavedAt(savedAt: String) {

        // .edit() will return the editor where we can save the value.
        preference.edit().putString(
            KEY_SAVED_AT, savedAt
        ).apply() // apply to save the values.
    }

    /**
     * @return sharedPreference (1st parameter) if exist or returns null(2nd parameter)
     */
    fun getLastSavedAt(): String? {
        return preference.getString(KEY_SAVED_AT, null)
    }
}