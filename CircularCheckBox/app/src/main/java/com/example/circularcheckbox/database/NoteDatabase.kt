package com.example.circularcheckbox.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.circularcheckbox.dao.NoteDao
import com.example.circularcheckbox.entities.Notes
import com.example.circularcheckbox.utils.subscribeOnBackground

@Database(entities = [Notes::class], version = 1)
abstract class NoteDatabase : RoomDatabase() {

    abstract fun noteDao(): NoteDao

    companion object {

        var instance: NoteDatabase? = null

        @Synchronized
        fun getInstance(context: Context): NoteDatabase {

            //  Create a new instance if null
            if (instance == null) {

                instance = Room.databaseBuilder(
                    context.applicationContext,
                    NoteDatabase::class.java,
                    "notes_db"

                ).fallbackToDestructiveMigration()
                    .addCallback(roomCallback)
                    .build()
            }

            return instance!!
        }

        //  Callback
        private val roomCallback = object : Callback() {

            //  DB onCreate method
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                populateDatabase(instance!!)
            }
        }

        //  Populate database
        private fun populateDatabase(db: NoteDatabase) {

            val noteDao = db.noteDao()

            subscribeOnBackground {
                noteDao.insert(Notes(1, "Go to the office", "Do some work", false))
                noteDao.insert(Notes(2, "Talk to Sheilla", "Do some work", false))
                noteDao.insert(Notes(3, "Verify some duties", "Do some work", false))
                noteDao.insert(Notes(4, "Write some code", "Do some work", false))
            }
        }
    }

}





















