package com.cogniwire.outstandingreceivables.db

import androidx.lifecycle.LiveData
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord
import com.cogniwire.outstandingreceivables.db.dao.ReceivablesDAO

class RecordRepo(private val receivablesDAO: ReceivablesDAO) {                                          //Respository to pull data from the DB via DAO as list
    val allPromissoryNotes: LiveData<MutableList<ReceivablesRecord>> = receivablesDAO.getAllPromissoryNotes()

    suspend fun insert(promissoryNote: ReceivablesRecord) {
        receivablesDAO.insert(promissoryNote)
    }

    suspend fun delete(promissoryNote: ReceivablesRecord) {
        receivablesDAO.delete(promissoryNote)
    }

    suspend fun update(promissoryNote: ReceivablesRecord) {
        receivablesDAO.update(promissoryNote)
    }

    suspend fun deleteAllPromissoryNotes() {
        receivablesDAO.deleteAllPromissoryNotes()
    }
}