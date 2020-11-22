package com.shiva.samplekotlincoroutineskodeindependencyinjectionroomjetpackmvvm.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

// By using @Entity annotation Room will be notified that this is an entity class.
// If we want to change the table name just write @Entity(tableName = "shiva")
@Entity
data class Quote(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val quote: String?,
    val author: String?,
    val thumbnail: String?,
    val created_at: String?,
    val updated_at: String?
)