package com.cogniwire.outstandingreceivables.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord


@Dao
interface ReceivablesDAO {                                                                          //Data Access Object
    @Insert
    suspend fun insert(promissoryNote: ReceivablesRecord)                                           //suspend for async data insert integrity

    @Delete
    suspend fun delete(promissoryNote: ReceivablesRecord)                                           //suspend for async data delete integrity

    @Update
    suspend fun update(promissoryNote: ReceivablesRecord)                                           //suspend for async data update integrity

    @Query("DELETE FROM promissory_notes_table")                                                    //suspend for async data delete * integrity
    suspend fun deleteAllPromissoryNotes()

    @Query("SELECT *FROM promissory_notes_table ORDER BY borrower ASC")
    fun getAllPromissoryNotes(): LiveData<MutableList<ReceivablesRecord>>
}