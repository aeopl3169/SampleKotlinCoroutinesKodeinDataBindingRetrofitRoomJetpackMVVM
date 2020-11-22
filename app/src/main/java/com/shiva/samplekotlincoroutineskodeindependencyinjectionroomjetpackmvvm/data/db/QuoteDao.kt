package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote

// For handling DataBase operations we use DAO (Data Access Object). DAO is always interface
// By using @Dao annotation Room will be notified that this is an DAO
@Dao
interface QuoteDao {
    // @Insert annotation to notify the Room to insert into the DB.
    // If any conflicts "OnConflictStrategy.REPLACE" will be replaced with current user with new user.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun saveAllQuotes(quote: List<Quote>) // This function is not needed to be suspend

    @Query("SELECT * FROM Quote")
    fun getQuotes(): LiveData<List<Quote>>
}