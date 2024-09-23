package com.cogniwire.outstandingreceivables.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "promissory_notes_table")
data class ReceivablesRecord(                                                                       //DB column definitions
    val borrower: String,
    val currency: String,
    val amount: String,
    val reason: String,
    val date: String,

) {
    @PrimaryKey(autoGenerate = true)
    var id = 0
}