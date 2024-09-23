package com.cogniwire.outstandingreceivables.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord
import com.cogniwire.outstandingreceivables.db.ReceivablesDB
import com.cogniwire.outstandingreceivables.db.RecordRepo
import kotlinx.coroutines.launch

class RecViewModel(application: Application) : AndroidViewModel(application) {
    val allPromissoryNotes: LiveData<MutableList<ReceivablesRecord>>
    val repository: RecordRepo

    init {
        val dao = ReceivablesDB.getInstance(application).getReceivablesDao()
        repository = RecordRepo(dao)
        allPromissoryNotes = repository.allPromissoryNotes
    }

    fun addPromissoryNote(promissoryNote: ReceivablesRecord) = viewModelScope.launch {
        repository.insert(promissoryNote)
    }

    fun updatePromissoryNote(promissoryNote: ReceivablesRecord) = viewModelScope.launch {
        repository.update(promissoryNote)
    }

    fun deletePromissoryNote(promissoryNote: ReceivablesRecord) = viewModelScope.launch {
        repository.delete(promissoryNote)
    }

    fun deleteAllPromissoryNotes() = viewModelScope.launch {
        repository.deleteAllPromissoryNotes()
    }
}