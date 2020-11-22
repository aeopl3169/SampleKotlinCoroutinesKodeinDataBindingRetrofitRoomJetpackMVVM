package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// Storing only single user ID.
const val CURRENT_USER_ID = 0

// By using @Entity annotation Room will be notified that this is an entity class. If we want to change the table name just write @Entity(tableName = "shiva")
@Entity
data class User(
    var id: Int? = null,
    var name: String? = null,
    var email: String? = null,
    var password: String? = null,
    var email_verified_at: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null
) {
    // User table
    // Here id will not autoincrement.
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID
}