package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.CURRENT_USER_ID
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User

// For handling DataBase operations we use DAO (Data Access Object). DAO is always interface
// By using @Dao annotation Room will be notified that this is an DAO
@Dao
interface UserDao {
    // "upsert" function will perform inserting data into the DB, so annotate with
    // @Insert annotation to notify the Room.
    // If any conflicts "OnConflictStrategy.REPLACE" will be replaced with current user with new user.
    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun upsert(user: User): Long
    suspend fun upsert(user: User): Long

    // @Query annotation is used to Retrieve data from DB.
    @Query("SELECT * FROM user WHERE uid = $CURRENT_USER_ID")
    fun getUser(): LiveData<User>
}