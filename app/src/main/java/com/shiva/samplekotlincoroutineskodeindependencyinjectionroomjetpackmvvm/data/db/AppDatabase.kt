package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.Quote
import com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities.User

// The class which extends "RoomDatabase" should be abstract and annotate with @Database along with entities and version.
// Here we have only one entity.
@Database(
    entities = [User::class, Quote::class],
    version = 1
)
abstract class AppDatabase : RoomDatabase() {
    // "getUserDao" function to save the user to DB
    abstract fun getUserDao(): UserDao
    abstract fun getQuoteDao(): QuoteDao

    // companion object to create our app DataBase.
    companion object {
        // By using @Volatile annotation, immediately it is visible to all the threads.
        @Volatile
        private var instance: AppDatabase? = null

        // For maintaining single instances throughout the app - Singleton
        private val LOCK = Any()

        // Creating DB
        // ?: right side will execute if left side is null. If left side(instance) is there, it will return.
        // By using synchronized keyword, only one thread will be running at once. There is no chance of multithreading.
        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            // Again checking instance is null or not with ?: . If it is null we build DB.
            instance ?: buildOwnDatabase(context).also {
                // assigning the created DB
                instance = it
            }
        }

        // this function returns database.
        private fun buildOwnDatabase(context: Context) =
        // 1st parameter - Since we are creating DB, should use applicationContext.
        // In fragment, even if we pass context it will take as applicationContext.
        // 2nd parameter - Class which is extended with RoomDatabase
        // 3rd parameter - DB name.
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "MyDatabase.db"
            ).build() // To build we use the build()
    }
}