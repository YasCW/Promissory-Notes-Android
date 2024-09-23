package com.cogniwire.outstandingreceivables.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.cogniwire.outstandingreceivables.db.entities.ReceivablesRecord
import com.cogniwire.outstandingreceivables.db.dao.ReceivablesDAO

@Database(entities = arrayOf(ReceivablesRecord::class), version = 10, exportSchema = false)

abstract class ReceivablesDB : RoomDatabase() {

    abstract fun getReceivablesDao(): ReceivablesDAO

    companion object {

        private var INSTANCE: ReceivablesDB? = null

        fun getInstance(context: Context): ReceivablesDB
        {
            return INSTANCE ?: synchronized(this)                      //return only the current instance (thread safe) or...

            {
                val instance = Room.databaseBuilder(                        //if instance is null - then create it
                    context.applicationContext,
                    ReceivablesDB::class.java,
                    "promissory_note_database"
                )
                    .fallbackToDestructiveMigration()                       //For development build purposes. Used in conjunction with
                    .build()                                                //version number increment arg in above @Database annotation

                INSTANCE = instance
                // return the instance
                instance
            }
        }
    }
}